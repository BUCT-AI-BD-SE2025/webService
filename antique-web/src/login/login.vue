<template>
  <v-app>
    <v-main class="accent">
      <v-container fill-height fluid @keyup.enter="submit" background-image>
        <v-layout align-center justify-center>
          <v-form v-if="isLogin">
           
            <v-card
              color="transparent"
              width="320"
              dark
              outlined
              elevation="24"
            >
              <v-text-field
                label="账号"
                v-model="account"
                loader-height="20"
                color="#004D40"
                outlined
                :error-messages="accountErrors"
              ></v-text-field>
              <v-text-field
                color="#004D40"
                outlined
                :type="showPW ? 'text' : 'password'"
                label="密码"
                v-model="password"
                :append-icon="showPW ? 'mdi-eye' : 'mdi-eye-off'"
                :error-messages="passwordErrors"
                filled
                @click:append="showPW = !showPW"
              ></v-text-field>
              <v-card-actions class="px-0">
                <v-btn
                  color="secondary"
                  depressed
                  block
                  large
                  :loading="loading"
                  @click="submit"
                  >登录</v-btn
                >
              </v-card-actions>
              <v-card-actions>
                <!-- <a href="#" class="red--text">修改密码</a> -->
                 <a href="#" class="red--text" @click="showRegister">注册</a> 
                <v-spacer></v-spacer>
                <a href="#" class="red--text" @click="clienkForget">忘记密码？</a>
              </v-card-actions>
            </v-card>
          </v-form>
          <v-form v-if="isRegister">
            <a href="#" class="red--text" @click="bakLogin">已有账号？登录</a> 
            <v-card
              color="transparent"
              width="320"
              dark
              outlined
              elevation="24"
            >
              <v-text-field
                label="账号"
                v-model="account"
                loader-height="20"
                color="#004D40"
                outlined
                :error-messages="accountErrors"
              ></v-text-field>
              <v-text-field
                color="#004D40"
                outlined
                :type="showPW ? 'text' : 'password'"
                label="密码"
                v-model="password"
                :append-icon="showPW ? 'mdi-eye' : 'mdi-eye-off'"
                :error-messages="passwordErrors"
                filled
                @click:append="showPW = !showPW"
              ></v-text-field>
              <v-text-field
                color="#004D40"
                outlined
                :type="showPW ? 'text' : 'password'"
                label="确认密码"
                v-model="respassword"
                :append-icon="showPW ? 'mdi-eye' : 'mdi-eye-off'"
                :error-messages="passwordErrors"
                filled
                @click:append="showPW = !showPW"
              ></v-text-field>
              <v-text-field
                v-model="email"
                outlined
                :rules="[rules.required, rules.email]"
                label="邮箱"
                append-outer-icon="mdi-send"
                @click:append-outer="sendEmail(1)"
              ></v-text-field>
              <v-text-field
                v-model="code"
                label="邮箱验证码"
              ></v-text-field>
              <v-card-actions class="px-0">
                <v-btn
                  color="secondary"
                  depressed
                  block
                  large
                  :loading="loading"
                  @click="register"
                  >注册</v-btn
                >
              </v-card-actions>
            </v-card>
          </v-form>
          <v-form v-if="isForget">
            <a href="#" class="red--text" @click="bakLogin">已有账号？登录</a> 
            <v-card
              color="transparent"
              width="320"
              dark
              outlined
              elevation="24"
            >
              <v-text-field
                label="账号"
                v-model="account"
                loader-height="20"
                color="#004D40"
                outlined
                :error-messages="accountErrors"
              ></v-text-field>
              <v-text-field
                color="#004D40"
                outlined
                :type="showPW ? 'text' : 'password'"
                label="密码"
                v-model="password"
                :append-icon="showPW ? 'mdi-eye' : 'mdi-eye-off'"
                :error-messages="passwordErrors"
                filled
                @click:append="showPW = !showPW"
              ></v-text-field>
              <v-text-field
                color="#004D40"
                outlined
                :type="showPW ? 'text' : 'password'"
                label="确认密码"
                v-model="respassword"
                :append-icon="showPW ? 'mdi-eye' : 'mdi-eye-off'"
                :error-messages="passwordErrors"
                filled
                @click:append="showPW = !showPW"
              ></v-text-field>
              <v-text-field
                v-model="email"
                outlined
                :rules="[rules.required, rules.email]"
                label="邮箱"
                append-outer-icon="mdi-send"
                @click:append-outer="sendEmail(2)"
              ></v-text-field>
              <v-text-field
                v-model="code"
                label="邮箱验证码"
              ></v-text-field>
              <v-card-actions class="px-0">
                <v-btn
                  color="secondary"
                  depressed
                  block
                  large
                  :loading="loading"
                  @click="updatePassword"
                  >修改密码</v-btn
                >
              </v-card-actions>
            </v-card>
          </v-form>
        </v-layout>
      </v-container>
    </v-main>
    <!-- <v-snackbar :color="snackbar.color" v-model="snackbar.show" class="mt-12" absolute top>
      {{ snackbar.text }}
      <v-btn text @click="snackbar.show=false">关闭</v-btn>
    </v-snackbar> -->
  </v-app>
</template>

<script>
import { validationMixin } from "vuelidate";
import { required, maxLength } from "vuelidate/lib/validators";
import { mapMutations } from "vuex";

export default {
  name: "Login",
  data() {
    return {
      account: "", // 账号
      password: "", // 密码
      respassword:"",//确认密码
      showPW: false, // 是否显示密码
      loading: false, // 登录中
      snackbar: {
        show: false,
        color: "grey darken-4",
        text: "",
      },
      isLogin:true,// 登录
      isRegister:false, // 注册
      isForget:false,// 忘记
      isEdit:false, // 修改
      email: '',
      code:'',
      rules: {
          required: value => !!value || 'Required.',
          counter: value => value.length <= 20 || 'Max 20 characters',
          email: value => {
            const pattern = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
            return pattern.test(value) || 'Invalid e-mail.'
          },
        },
    };
  },
  mixins: [validationMixin],
  validations: {
    account: { required, maxLength: maxLength(30) },
    password: { required },
  },
  computed: {
    accountErrors() {
      const err = [];
      if (!this.$v.account.$dirty) return err;
      !this.$v.account.required && err.push("账号还没填呢");
      !this.$v.account.maxLength && err.push("账号太长了");
      return err;
    },
    passwordErrors() {
      const err = [];
      if (!this.$v.password.$dirty) return err;
      !this.$v.password.required && err.push("密码还没填呢");
      return err;
    },
  },
  created() {},
  methods: {
    bakLogin(){
      this.isLogin = true;
      this.isForget = false;
      this.isRegister = false;
    },
    clienkForget(){
      this.isForget = true;
      this.isLogin  = false;
    },
    sendEmail(type){
      if(this.account && this.email){
        let url = "register/sendEmail";
        if(type == 2){
          url = "register/sendEmailByName"
        }
        this.$post(url, {
          username: this.account,
          email:this.email
        })
          .then((r) => {
            this.$message.success("邮件已发送");
          })
          .catch((e) => {
            this.$message.error("邮件发送失败");
          });
      }else{
         this.$message.error("用户名/邮箱不能为空");
      }
    },
    showRegister(){
      this.isLogin = false;
      this.isRegister = true;
    },

    /***
     *  注册
     */
    register(){
      if(this.password == this.respassword){
        this.$v.$touch();
      if (!this.$v.$invalid) {
        this.loading = true;
        this.$post("register/add", {
          username: this.account,
          password: this.password,
          respassword: this.respassword,
          email:this.email,
          code:this.code
        })
          .then((r) => {
            this.isLogin = true;
            this.isRegister = false;
            this.$message.success("注册成功");
            this.loading = false;
          })
          .catch((e) => {
            this.$message.error("注册失败");
          });
      }
      }else{
         this.$message.error("重复密码不匹配");
      }
    },
    updatePassword(){
      if(this.password == this.respassword){
        this.$v.$touch();
      if (!this.$v.$invalid) {
        this.loading = true;
        this.$post("register/updatePassword", {
          username: this.account,
          password: this.password,
          code:this.code
        })
          .then((r) => {
            this.isLogin = true;
            this.isForget = false;
            this.$message.success("修改成功");
            this.loading = false;
          })
          .catch((e) => {
            this.loading = false;
            this.$message.error("修改失败");
          });
      }
      }else{
         this.$message.error("重复密码不匹配");
      }
    },
    submit() {
      this.$v.$touch();
      if (!this.$v.$invalid) {
        this.loading = true;
        this.$post("login", {
          username: this.account,
          password: this.password,
        })
          .then((r) => {
            let data = r.data.data;
            this.saveLoginData(data);
            setTimeout(() => {
              this.loading = false;
            }, 500);
            // this.$router.push("/");
          })
          .catch((e) => {
            console.log(e);
            setTimeout(() => {
              this.loading = false;
            }, 500);
          });
      }
    },
    ...mapMutations({
      setToken: "account/setToken",
      setExpireTime: "account/setExpireTime",
      setPermissions: "account/setPermissions",
      setRoles: "account/setRoles",
      setMenus: "account/setMenus",
      setUser: "account/setUser",
      SET_DRAWER: "account/SET_DRAWER",
    }),
    saveLoginData(data) {
      this.setToken(data.token);
      this.setExpireTime(data.exipreTime);
      this.setUser(data.user);
      this.setPermissions(data.permissions);
      this.setRoles(data.roles);
      //this.setMenus(data.menus);
      if (data.menus && data.menus.length > 0) {
        let routs = [];
        data.menus.forEach((menu) => {
          if (menu.type == 1) {
            routs.push(menu);
          }
        });
        this.setMenus(routs);
      }
      this.$router.push("/");
    },
  },
};
</script>
<style scoped>
.background-image {
  background-image: url("/001.png"); /* 使用相对路径或绝对路径指向你的图片 */
  background-size: cover; /* 根据需要调整背景图大小 */
  background-position: center; /* 根据需要调整背景图位置 */
  background-repeat: no-repeat; /* 防止背景图重复 */
  height: 100vh; /*根据需要设置容器高度，例如占满视口高度 */
}
</style>