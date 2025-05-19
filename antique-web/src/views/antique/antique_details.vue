<template>
  <v-container>
    <v-row justify="center">
      <v-col cols="8">
        <v-card>
          <v-list three-line color="#f4f5f8">
            <v-list-item>
              <v-list-item-avatar
                tile
                height="210"
                width="180"
                @click="dialog = true"
              >
                <v-carousel
                  cycel
                  hide-delimiter-background
                  show-arrows-on-hover
                  delimiter-icon="mdi-minus"
                  height="210"
                  width="180"
                >
                  <template v-slot:prev="{ on, attrs }">
                    <v-icon v-bind="attrs" v-on="on">mdi-chevron-left</v-icon>
                  </template>
                  <template v-slot:next="{ on, attrs }">
                    <v-icon v-bind="attrs" v-on="on">mdi-chevron-right</v-icon>
                  </template>
                  <v-carousel-item
                    v-for="(item, i) in imgs"
                    :key="i"
                    :src="item.path"
                    reverse-transition="fade-transition"
                    transition="fade-transition"
                    contain
                  ></v-carousel-item>
                </v-carousel>
              </v-list-item-avatar>
              <v-list-item-content>
                <v-list-item-title>
                  <span class="text-h2 font-weight-black">{{
                    antique.nameCn
                  }}</span>
                </v-list-item-title>
                <v-list-item-title>
                  <span class="text-h5 font-weight-black"
                    >年代：
                      antique.era
                  </span>
                </v-list-item-title>
                <v-list-item-title>
                  <span class="text-h5 font-weight-black"
                    >博物馆：
                      {{ antique.museum }}
                  </span>
                </v-list-item-title>
                <v-list-item-title>
                  <span class="text-h5 font-weight-black"
                    >时间：{{ antique.dateStr }}
                  </span>
                </v-list-item-title>
                <v-list-item-title>
                  <span class="text-h5 font-weight-black"
                    >来源：
                    <a href="#" @click="fromClieck(antique.fromUrl)">{{ antique.fromUrl}}</a>
                  </span>
                </v-list-item-title>
              </v-list-item-content>
            </v-list-item>
          </v-list>

          <template>
            <v-dialog v-model="dialog">
              <v-card>
                <v-card-title color="#f4f5f8">
                  <v-card-actions>
                    <v-icon large right @click="closeImg"> mdi-close</v-icon>
                  </v-card-actions>
                </v-card-title>
                <v-carousel
                  cycel
                  hide-delimiter-background
                  show-arrows-on-hover
                  delimiter-icon="mdi-minus"
                  height="700"
                >
                  <v-carousel-item
                    v-for="(item, i) in imgs"
                    :key="i"
                    reverse-transition="fade-transition"
                    transition="fade-transition"
                  >
                    <v-img
                      :src="item.path"
                      :lazy-src="item.path"
                      contain
                      height="700"
                    ></v-img>
                  </v-carousel-item>
                  <!-- <v-row><br /><br /><br /><br /><br /></v-row> -->
                </v-carousel>
              </v-card>
            </v-dialog>
          </template>
        </v-card>
      </v-col>
    </v-row>
    <v-row justify="center">
      <v-col cols="8">
        <card>
          <v-row>
            <p class="text-h2 ml-8 mt-5">简介</p>
          </v-row>
          <v-row>
            <v-col class="ml-5 mr-5">
              <p class="text-justify" v-html="antiqueRemark"></p>
            </v-col>
          </v-row>
        </card>
      </v-col>
    </v-row>
    <v-row justify="center" class="mt-5">
      <v-col cols="8">
        <card>
          <v-row>
            <p class="text-h2 ml-8 mt-5">详情</p>
          </v-row>
          <v-row>
            <v-col class="ml-5 mr-5">
              <p class="text-justify" v-html="antiqueDetails"></p>
            </v-col>
          </v-row>
        </card>
      </v-col>
    </v-row>
    
    <v-row justify="center" class="mt-5">
      <v-col cols="8">
        <card>
          <v-row>
            <p class="text-h2 ml-8 mt-5">推荐</p>
          </v-row>
          <v-row>
            <!-- <v-col class="ml-5 mr-5">
              <p class="text-justify" v-html="antiqueDetails"></p>
            </v-col> -->
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
              </v-col>
          </v-row>
        </card>
      </v-col>
    </v-row>
    <v-row><br /><br /><br /> </v-row>
  </v-container>
</template>
  
<script>
import Card from "../../components/base/Card.vue";
export default {
  components: { Card },
  data: () => ({
    dialog: false,
    fbdialog: false,
    loading: false,
    imgval: [],
    antiqueList: [],
    antique: {
      nameCn: "",
      era: "",
      museum: "",
      remark:"",
      details:"",
      dateStr:""
      
    },
    // fb: {
    //   name: "",
    //   content: "",
    //   email: "",
    //   imgs: [],
    // },
    // fbd: {
    //   name: "",
    //   content: "",
    //   email: "",
    //   imgs: [],
    // },
    imgs: [],
    items: [
      {
        color: "#1F7087",
        src: "https://cdn.vuetifyjs.com/images/cards/foster.jpg",
        title: "Supermodel",
        artist: "Foster the People",
      },
    ],
  }),
  mounted() {},
  computed: {
    antiqueDetails() {
      return this.antique.details.replace(/\n/g, "<br />");
    },
    antiqueRemark() {
      if (this.antique.remark) {
        return this.antique.remark.replace(/\n/g, "<br />");
      }
    },
  },

  watch: {},
  created() {
    let antiqueId = this.$route.query.antiqueId;
    this.getAntiqueInfo({ antiqueId: antiqueId });
    this.getImgs(antiqueId);
    this.getAntiqueList();
  },

  methods: {
    closeImg() {
      this.dialog = false;
    },
    getAntiqueInfo(params) {
      this.$get("/antique-info/byId", params).then((result) => {
        let data = result.data;
        this.antique = data;
      });
    },
    getImgs(bookId) {
      this.$get("/antique-info-file/list/" + bookId, {}).then((result) => {
        let data = result.data;
        if (data == null || data.length == 0) {
          this.imgs.push({ path: "/favicon.png" });
          return;
        }
        data.forEach((imgInfo) => {
          imgInfo["path"] =
            process.env.VUE_APP_API_BASE_URL + "image/" + imgInfo.filePath;
        });
        this.imgs = data;
      });
    },
    fromClieck(url){
      window.open(url)
    },
        /***
     * 获取书籍列表
     */
    getAntiqueList() {
      let params = {
        pageNum: Math.floor(Math.random() * (20 - 1 + 1)) + 1,
        pageSize: 6,
        museum: this.antique.museum,
        era: this.antique.era,
      }
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
              console.log(
                process.env.VUE_APP_API_BASE_URL + "image/" + imgInfo.filePath
              );
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
