<template>
  <v-app-bar id="app-bar" absolute app color="transparent" flat height="45">
    <v-container>
      <v-row>
        <v-col md="2" sm="2" lg="2" cols="2">
          <v-tooltip bottom>
            <template v-slot:activator="{ on, attrs }">
              <v-toolbar-title
                class="hidden-sm-and-down font-weight-light text-h2 text-center"
                @click="toHome"
                v-bind="attrs"
                v-on="on"
                >海外文物</v-toolbar-title
              >
            </template>
            <span>海外流失文物</span>
          </v-tooltip>
        </v-col>
      </v-row>
    </v-container>
    <v-spacer />
    <div class="mx-3" />
    <v-menu
      bottom
      left
      offset-y
      origin="top right"
      transition="scale-transition"
    >
      <template v-slot:activator="{ on, attrs }">
        <v-btn
      depressed
       v-bind="attrs" v-on="on"
    >
      <v-icon class="mr-5" min-width="0" text
          >mdi-account</v-icon
        >
      {{name}}
    </v-btn>
       
      </template>
      <v-list :tile="false" nav class="el-dropdown-link">
        <v-list-item>
          <v-list-item-title @click="updatePassword"
            >修改密码</v-list-item-title
          >
        </v-list-item>
        <v-list-item>
          <v-list-item-title @click="logOut">LOGOUT</v-list-item-title>
        </v-list-item>
      </v-list>
    </v-menu>
    <template>
      <v-dialog v-model="dialog" max-width="350px" @click:outside="close">
        <v-card>
          <v-card-title>
            <span class="text-h5">修改密码</span>
          </v-card-title>
          <v-card-text>
            <v-container>
              <v-row>
                <v-col cols="12" sm="12" md="12" class="mt-n5">
                  <v-text-field
                    v-model="oldpassword"
                    label="旧密码"
                    type="password"
                  ></v-text-field>
                </v-col>
                <v-col cols="12" sm="12" md="12" class="mt-n5">
                  <v-text-field
                    v-model="newpassword"
                    type="password"
                    label="新密码"
                  ></v-text-field>
                </v-col>

                <v-col cols="12" sm="12" md="12" class="mt-n5">
                  <v-text-field
                    v-model="respassword"
                    type="password"
                    label="确认密码"
                  ></v-text-field>
                </v-col>
              </v-row>
            </v-container>
          </v-card-text>

          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="red" outlined @click="close">取消</v-btn>
            <v-btn color="success" outlined :loading="loading" @click="save"
              >修改</v-btn
            >
          </v-card-actions>
        </v-card>
      </v-dialog>
    </template>
  </v-app-bar>
</template>

<script>
// Components
import { VHover, VListItem } from "vuetify/lib";

// Utilities
import { mapState, mapMutations } from "vuex";
import db from "@/utils/localstorage";

export default {
  name: "DashboardCoreAppBar",

  components: {
    AppBarItem: {
      render(h) {
        return h(VHover, {
          scopedSlots: {
            default: ({ hover }) => {
              return h(
                VListItem,
                {
                  attrs: this.$attrs,
                  class: {
                    "black--text": !hover,
                    "white--text secondary elevation-12": hover,
                  },
                  props: {
                    activeClass: "",
                    dark: hover,
                    link: true,
                    ...this.$attrs,
                  },
                },
                this.$slots.default
              );
            },
          },
        });
      },
    },
  },

  props: {
    value: {
      type: Boolean,
      default: false,
    },
  },

  data: () => ({
    notifications: ["Log Out", "修改密码"],
    loading: false,
    oldpassword: "",
    newpassword: "",
    respassword: "",
    dialog: false,
  }),

  computed: {
    ...mapState(["account"]),
    name: function(){
      let user = db.get("USER");
      return JSON.parse(user).userName
    }
  },

  methods: {
    save() {
      this.loading = true;
      if(this.newpassword != this.respassword){
        this.$message.error("密码重复不一致");
        this.loading = false;
        return;
      }
      this.$post("user/updatePassword", {
          oldPassword: this.oldpassword,
          newPassword:this.newpassword
        })
          .then((r) => {
            this.loading = false;
            this.$message.success("密码修改成功");
            this.logOut();
          })
          .catch((e) => {
            this.loading = false;
            this.$message.error("密码修改失败");
          });
    },
    close() {
      this.dialog = false;
      this.newpassword = false;
      this.oldpassword = false;
      this.respassword = false;
    },
    ...mapMutations({
      setDrawer: "account/SET_DRAWER",
    }),
    logOut() {
      localStorage.clear(); //清除浏览器缓存
      this.$get("login/out", {}); // 清除服务器缓存
      this.$router.push("/login");
    },
    toHome() {
      if (this.$route.path == "/") {
        this.$router.go(0);
      } else {
        this.$router.push("/");
      }
    },
    /***
     *  密码
     */
    updatePassword() {
      this.dialog = true;
    },
  },
};
</script>

<style>
.el-dropdown-link {
  line-height: 30px;
  cursor: pointer;
}
</style>
