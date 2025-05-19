<template>
  <v-bottom-navigation height="40" grow fixed>
    <v-container>
      <v-row>
        <v-col cols="12" class="text-center">
          <div class="body-1 font-weight-light text-center">
            <v-icon size="18"> mdi-headset</v-icon>&nbsp;
          </div>
        </v-col>
        <template>
          <v-dialog v-model="contactDialog" max-width="290">
            <v-card>
              <v-card-title class="text-h5"> 关于我们 </v-card-title>
              <v-card-text>
                本网站的内容来源于互联网。<br />如发现任何内容侵犯了您的版权或其他合法权益，请通过以下联系方式与我们取得联系，我们将及时处理并移除相关内容。<br />
                联系邮箱：[<a href="#">xxxxxxx@qq.com</a>] <br />
                <!-- 联系电话：[联系电话] -->
              </v-card-text>
            </v-card>
          </v-dialog>
        </template>
        <template>
          <v-dialog v-model="myDialog" max-width="800">
            <v-card>
              <v-card-title class="text-h3"> 感谢赞助 </v-card-title>
              <v-card-text>
                <v-container>
                  <v-row
                    ><v-col cols="4" v-for="(img, i) in imgs" :key="i">
                      <v-card>
                        <v-img
                          :src="img.path"
                          class="grey darken-4"
                          contain
                        ></v-img>
                        <v-card-title class="text-h6">
                          {{ img.name }}
                        </v-card-title>
                      </v-card>
                    </v-col></v-row
                  >
                </v-container>
              </v-card-text>
            </v-card>
          </v-dialog>
        </template>
      </v-row>
    </v-container>
  </v-bottom-navigation>
</template>

<script>
export default {
  name: "DashboardCoreFooter",

  data: () => ({
    links: [],
    contactDialog: false,
    myDialog: false,
    imgs: [
      { path: "/sponsor1.jpg", name: "请吃泡面" },
      { path: "/sponsor2.jpg", name: "请喝咖啡" },
      { path: "/sponsor3.jpg", name: "随意打赏" },
    ],
  }),
  methods: {
    /***
     *  打开联系页面
     */
    openContactV() {
      this.contactDialog = true;
    },
  },
};
</script>

<style lang="sass">
#dashboard-core-footer
  a
    font-size: .825rem
    font-weight: 500
    text-decoration: none
    text-transform: uppercase
</style>
