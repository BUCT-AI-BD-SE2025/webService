  <template>
  <v-card class="mt-3">
    <v-row :style="{ backgroundColor: '#CFD8DC' }">
      <v-col cols="1"></v-col>
      <v-col
        cols="10"
        sm="4"
        md="3"
        lg="2"
        style="height: 60px"
        @keyup.enter="search"
        class="ml-n12 mt-n3"
      >
        <v-select
          :items="museumList"
          v-model="museum"
          label="博物馆"
          :clearable="true"
        ></v-select>
      </v-col>
      <v-col
        cols="10"
        sm="4"
        md="3"
        lg="2"
        style="height: 60px"
        @keyup.enter="search"
        class="mt-n3"
      >
        <v-select :items="eraList" v-model="era" :clearable="true" label="年代"></v-select>
      </v-col>
      <v-col
        cols="10"
        sm="4"
        md="3"
        lg="2"
        style="height: 60px"
        @keyup.enter="search"
      >
        <v-text-field
          class="mt-n3"
          style="border-radius: 0"
          background-color="transparent"
          single-line
          label="关键字"
          append-icon="mdi-book-search-outline"
          v-model="keyword"
          @click:append="search"
        ></v-text-field>
      </v-col>
    </v-row>
    <div>
      <v-container fluid>
        <v-row justify="center">
          <v-col cols="12">
            <v-row justify="start">
              <v-col
                cols="6"
                sm="4"
                md="3"
                lg="2"
                v-for="(book, k) in antiqueList"
                :key="book.k"
              >
                <!-- height="255" -->
                <v-card
                  elevation="24"
                  width="200"
                  height="255"
                  class="mx-auto"
                  :key="k"
                  @click="details(book)"
                >
                  <!-- <v-system-bar lights-out></v-system-bar> -->
                  <v-carousel
                    cycel
                    hide-delimiter-background
                    show-arrows-on-hover
                    delimiter-icon="mdi-minus"
                    height="200"
                  >
                    <template v-slot:prev="{ on, attrs }">
                      <!-- <v-btn color="success" v-bind="attrs" v-on="on">></v-btn> -->
                      <v-icon v-bind="attrs" v-on="on">mdi-chevron-left</v-icon>
                    </template>
                    <template v-slot:next="{ on, attrs }">
                      <v-icon v-bind="attrs" v-on="on"
                        >mdi-chevron-right</v-icon
                      >
                    </template>
                    <v-carousel-item
                      v-for="(item, i) in book.fileList"
                      :key="i"
                      :src="item.path"
                      contain
                      reverse-transition="fade-transition"
                      transition="fade-transition"
                    ></v-carousel-item>
                  </v-carousel>
                  <v-tooltip bottom>
                    <template v-slot:activator="{ on, attrs }">
                      <v-container class="mt-2" v-bind="attrs" v-on="on">
                        <v-row align-content="center" justify="center">
                          <span class="font-weight-bold text-h4">{{
                            getStr(book.nameCn, 12, 9)
                          }}</span>
                        </v-row>
                        <v-row align-content="center" justify="center">
                          <span class="font-weight-light">{{
                            getStr(book.details, 15, 12)
                          }}</span>
                        </v-row>
                      </v-container>
                    </template>
                    <span v-html="book.nameCn" class="text-h3"></span>
                    <span v-html="strFormate(2, book.details)"></span>
                  </v-tooltip>
                </v-card>
              </v-col> </v-row
          ></v-col>
        </v-row>
      </v-container>

      <!-- </v-tab-item>
      </v-tabs-items> -->
    </div>
    <div class="text-center mt-n5">
      <v-container>
        <v-row justify="center">
          <v-col cols="4">
            <v-container class="max-width">
              <v-pagination
                v-model="page"
                class="my-4"
                :length="totalPage"
                style="height: 15px"
              ></v-pagination>
            </v-container>
          </v-col>
        </v-row>
        <v-row><br /><br /></v-row>
      </v-container>
    </div>
    <div ref="pieChart" style="width: 600px; height: 400px;"></div>
  </v-card>

</template>

<script>
import * as echarts from 'echarts';
import axios from 'axios';
export default {
  data: () => ({
    chart: null,
    museumList: [],
    museum: "",
    era: "",
    eraList: [],
    slideVal: 0,
    onboarding: 0,
    keyword: "",
    page: 1,
    totalPage: 1,
    antiqueList: [],
    pageSize: 12,
    previewDialog: false,

  }),
  mounted() {
    this.initChart();
    this.getAntiqueList({
      pageNum: this.page,
      pageSize: this.pageSize,
      keyword: this.keyword,
      museum: this.museum,
      era: this.era,
    });
    this.getMuseumList();
    this.getEraList();
  },
  computed: {

  },

  watch: {
    page(newPage, oldPage) {
      this.getAntiqueList({
        pageNum: newPage,
        pageSize: this.pageSize,
        keyword: this.keyword,
        museum: this.museum,
        era: this.era,
      });
    },
  },
  created() {
    this.producer = this.$route.params.producer
      ? this.$route.params.producer
      : "";
    this.designer = this.$route.params.designer
      ? this.$route.params.designer
      : "";
  },

  methods: {

    initChart() {
      const chartDom = this.$refs.pieChart;
      this.chart = echarts.init(chartDom);

      // ✅ 从后端获取数据，确保图表配置等数据准备好后再 setOption
      axios.get('http://localhost:9099/api/pie-data')
        .then(response => {
          const pieData = response.data;

          const option = {
            title: {
              text: '文物来源',
              left: 'center'
            },
            tooltip: {
              trigger: 'item'
            },
            legend: {
              orient: 'vertical',
              left: 'left'
            },
            series: [
              {
                name: '文物来源',
                type: 'pie',
                radius: '50%',
                data: pieData, // 用后端返回的数据
                emphasis: {
                  itemStyle: {
                    shadowBlur: 10,
                    shadowOffsetX: 0,
                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                  }
                }
              }
            ]
          };


          this.chart.setOption(option);
        })
        .catch(error => {
          console.error('获取饼图数据失败:', error);
        });
    },
    showCalendar() {
      this.$refs.calendarShow.openDiog();
    },
    nextVal(newValue) {
      this.slideVal = newValue;
    },
    nextVal2(newValue) {
      this.onboarding = newValue;
    },
    search() {
      this.page = 1;
      this.getAntiqueList({
        pageNum: this.page,
        pageSize: this.pageSize,
        keyword: this.keyword,
        museum: this.museum,
        era: this.era,
      });
    },

    /**
     *  获取博物馆列表
     */
    getMuseumList() {
      this.$get("/antique-info/museum", {}).then((result) => {
        this.museumList = [];
        result.data.forEach(mu=>{
          this.museumList.push(mu.museum + " ("+mu.ct+")")
        })
        //this.museumList = result.data
      });
    },
    /**
     *  获取年代列表
     */
    getEraList(){
      this.eraList = [];
      this.$get("/antique-info/era", {}).then((result) => {
        result.data.forEach(mu=>{
          this.eraList.push(mu.era + " ("+mu.ct+")")
        })
        // this.eraList = result.data
      });
    },
    /***
     * 获取书籍列表
     */
    getAntiqueList(params) {
      this.overlay = !this.overlay;
      this.$get("/antique-info/list", params).then((result) => {
        let data = result.data.records;
        this.page = result.data.current;
        this.totalPage = Math.ceil(result.data.total / this.pageSize);
        data.forEach((element) => {
          // let imgList = element.imgs;
          if (element.fileList == null || element.fileList.length == 0) {
            element.fileList = [{ path: "/favicon.png" }];
          } else {
            element.fileList.forEach((imgInfo) => {
              imgInfo["path"] =
                process.env.VUE_APP_API_BASE_URL + "image/" + imgInfo.filePath;
            });
          }
        });
        this.antiqueList = data;
        this.overlay = !this.overlay;
      });
    },
    /**
     *
     * @param type  字符串格式化
     * @param str
     */
    strFormate(type, str) {
      if (!str) {
        return "";
      }
      if (type == 1) {
        if (str.length < 10) {
          return str;
        }
        return str.substring(0, 6) + "...";
      }
      if (type == 2) {
        let ary = this.splitStringByLength(str, 30);
        let content = "</br>";
        ary.forEach((istr) => {
          content = content + istr + "</br>";
        });
        return content;
      }
    },
    splitStringByLength(str, length) {
      const regex = new RegExp(`.{1,${length}}`, "g");
      return str.match(regex) || [];
    },
    getStr(str, t1, t2) {
      if (str == undefined) {
        return "";
      }
      if (str.length < t1) {
        return str;
      }
      return str.substring(0, t2) + "...";
    },
    /**
     * 详情展示
     * @param
     */
    details(antique) {
      // this.$router.push({ path: "/book-details", query: { bookId: book.id } });
      const routeData = this.$router.resolve({
        path: "/antique-details?antiqueId=" + antique.id,
        antiqueId: antique.id,
      });
      // 使用 window.open 打开新窗口
      window.open(routeData.href, "_blank");
    },
  },
};
</script>
