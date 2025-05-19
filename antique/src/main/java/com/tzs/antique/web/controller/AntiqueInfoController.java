package com.tzs.antique.web.controller;


import cn.hutool.core.io.FileUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tzs.antique.system.entity.BaseUser;
import com.tzs.antique.web.dao.AntiqueInfoMapper;
import com.tzs.antique.web.entity.AntiqueInfo;
import com.tzs.antique.web.entity.AntiqueInfoFile;
import com.tzs.antique.web.service.IAntiqueInfoFileService;
import com.tzs.antique.web.service.IAntiqueInfoService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author YHS
 */
@RestController
@RequestMapping("/antique-info")
@Api(tags = "文物信息")
@Slf4j
public class AntiqueInfoController {

    @Resource
    private AntiqueInfoMapper infoMapper;

    @Resource
    private IAntiqueInfoService infoService;

    @Resource
    private IAntiqueInfoFileService fileService;

    @Resource
    private IndexWriter indexWriter;

    @Resource
    private Analyzer analyzer;
    @Resource
    private Directory directory;


    @GetMapping("/museum")
    public ResponseEntity museumList(){
        List<Map<String,Object>> list = infoMapper.museumByGroup();
        return ResponseEntity.ok(list);
    }
   @GetMapping("/era")
    public ResponseEntity eraList(){
        List<Map<String,Object>> list = infoMapper.eraByGroup();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/byId")
    public ResponseEntity byId(Integer antiqueId){
        AntiqueInfo antiqueInfo = infoMapper.selectById(antiqueId);
        return ResponseEntity.ok(antiqueInfo);
    }

    /***
     *  lucene 查询数据列表
     * @param keyword
     * @return
     */
    @GetMapping("/list")
    @RequiresPermissions("管理")
    public ResponseEntity list(@RequestParam(value = "museum", required = false) String museum,
                               @RequestParam(value = "era", required = false)String era
            , @RequestParam(value = "keyword", required = false)String keyword
            , Integer pageNum, Integer pageSize) throws ParseException, IOException {

        DirectoryReader reader = DirectoryReader.open(directory);
        IndexSearcher searcher = new IndexSearcher(reader);
        BooleanQuery.Builder builder = new BooleanQuery.Builder();

        if (!StringUtils.isBlank(museum)) {
            builder.add(new TermQuery(new Term("museum", museum)), BooleanClause.Occur.MUST);
        }
        if(!StringUtils.isBlank(era)){
            builder.add(new TermQuery(new Term("era", era)), BooleanClause.Occur.MUST);
        }
        if (!StringUtils.isBlank(keyword)) {
            String[] fields = {"nameCn", "details","remark"};
            QueryParser parser = new MultiFieldQueryParser(fields, analyzer);// 分词查询
            Query query = parser.parse(keyword);
            builder.add(query, BooleanClause.Occur.SHOULD);
            builder.setMinimumNumberShouldMatch(1);
        }
        if(StringUtils.isBlank(museum) && StringUtils.isBlank(era) && StringUtils.isBlank(keyword)){
            builder.add(new MatchAllDocsQuery(), BooleanClause.Occur.MUST);
        }
        BooleanQuery query = builder.build();
        Sort sort = new Sort(new SortField("id_sort", SortField.Type.LONG));
        TopDocs search = searcher.search(query, pageNum * pageSize,sort);
        long value = search.totalHits.value;
        ScoreDoc[] scoreDocs = search.scoreDocs;
        int start = (pageNum - 1) * pageSize;
        int end = Math.min(start + pageSize, scoreDocs.length);
        ScoreDoc[] pageScores = Arrays.copyOfRange(scoreDocs, start, end);
        Page<AntiqueInfo> antiqueInfoPage = new Page<>();
        antiqueInfoPage.setRecords(new ArrayList<>());
        antiqueInfoPage.setTotal(value).setSize(pageSize).setCurrent(pageNum);
        for (ScoreDoc scoreDoc : pageScores) {
            Document doc = searcher.doc(scoreDoc.doc);
            AntiqueInfo info = new AntiqueInfo();
            info.setNameCn(doc.get("nameCn")).setId(Integer.parseInt(doc.get("id")))
                    .setEra(doc.get("era")).setRemark(doc.get("remark"))
                    .setDetails(doc.get("details")).setArtist(doc.get("artist")).setMuseum(doc.get("museum"))
                    ;
            String files = doc.get("files");
            if(!StringUtils.isBlank(files)){
                String[] split = files.split("@\\$@");
                List<AntiqueInfoFile> list = new ArrayList<>();
                for (String url : split) {
                    AntiqueInfoFile file = new AntiqueInfoFile();
                    file.setFilePath(url);
                    list.add(file);
                }
                info.setFileList(list);
            }
            antiqueInfoPage.getRecords().add(info);
        }
        return ResponseEntity.ok(antiqueInfoPage);
    }


    /****
     *  初始化索引
     * @return
     * @throws IOException
     */
    @GetMapping("/index")
    public ResponseEntity genIndex() throws IOException {
        indexWriter.deleteAll(); // 清空旧索引
        indexWriter.commit();
        LambdaQueryWrapper<AntiqueInfo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        Integer pagetotal = 1;
        Map<Integer,List<AntiqueInfoFile>> fileMap = new HashMap<>();
        LambdaQueryWrapper<AntiqueInfoFile> files = new LambdaQueryWrapper<>();
        List<AntiqueInfoFile> list = fileService.list(files);
        for (int i = 0; i < list.size(); i++) {
            AntiqueInfoFile infoFile = list.get(i);
            Integer antiqueId = infoFile.getAntiqueId();
            if(!fileMap.containsKey(antiqueId)){
                ArrayList<AntiqueInfoFile> arrayList = new ArrayList<>();
                arrayList.add(infoFile);
                fileMap.put(antiqueId, arrayList);
                continue;
            }
            List<AntiqueInfoFile> antiqueInfoFiles = fileMap.get(antiqueId);
            antiqueInfoFiles.add(infoFile);
        }
        while (true){
            IPage<AntiqueInfo> page = new Page<>(pagetotal, 200);
            IPage<AntiqueInfo> antiqueInfoIPage = infoMapper.selectPage(page, lambdaQueryWrapper);
            List<AntiqueInfo> records = antiqueInfoIPage.getRecords();
            for (AntiqueInfo record : records) {
                Document doc = new Document();
                doc.add(new TextField("details", record.getDetails() == null ? "" :record.getDetails() , Field.Store.YES));
                doc.add(new TextField("remark", record.getRemark() == null ? "" :record.getRemark(), Field.Store.YES));

                doc.add(new StringField("id", record.getId().toString(), Field.Store.YES));
                doc.add(new NumericDocValuesField("id_sort", record.getId())); // 添加数值型排序字段

                doc.add(new StringField("nameCn", record.getNameCn() == null ? "" :record.getNameCn(), Field.Store.YES));
                doc.add(new StringField("era", record.getEra() == null ? "" :record.getEra(), Field.Store.YES));
                doc.add(new StringField("artist", record.getArtist() == null ? "" :record.getArtist(), Field.Store.YES));
                doc.add(new StringField("museum", record.getMuseum() == null ? "" :record.getMuseum(), Field.Store.YES));
                doc.add(new StringField("dateStr", record.getDateStr() == null ? "" :record.getDateStr(), Field.Store.YES));

                List<AntiqueInfoFile> antiqueInfoFiles = fileMap.get(record.getId());
                String collect = antiqueInfoFiles == null ? "" : antiqueInfoFiles.stream().map(AntiqueInfoFile::getFilePath).collect(Collectors.joining("@$@"));
                doc.add(new TextField("files",collect , Field.Store.YES));

                indexWriter.addDocument(doc);
            }
            indexWriter.commit(); // 提交变更
            if (antiqueInfoIPage.getPages() == pagetotal) {
                break;
            }
            pagetotal++;
        }
        return ResponseEntity.ok("索引创建完成");
    }




}
