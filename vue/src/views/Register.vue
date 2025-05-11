<template>
  <div class="register-wrapper">
    <div class="register-container">
      <h2>用户注册</h2>

      <input
        type="text"
        v-model="username"
        placeholder="请输入用户名"
      />
      <div class="error-message" v-if="error && !username">用户名不能为空</div>

      <input
        type="email"
        v-model="email"
        placeholder="请输入邮箱"
      />
      <div class="error-message" v-if="error && !email">邮箱不能为空</div>
      <div class="error-message" v-if="error && !validEmail">请输入有效的邮箱</div>

      <input
        type="password"
        v-model="password"
        placeholder="请输入密码"
      />
      <div class="error-message" v-if="error && !password">密码不能为空</div>

      <input
        type="password"
        v-model="confirmPassword"
        placeholder="确认密码"
      />
      <div class="error-message" v-if="error && confirmPassword !== password">
        两次密码输入不一致
      </div>

      <button @click="register">注册</button>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'RegisterView',
  data() {
    return {
      username: '',
      email: '',
      password: '',
      confirmPassword: '',
      error: false,
      validEmail: true
    };
  },
  methods: {
    register() {
      this.error = false;
      this.validEmail = this.validateEmail(this.email);

      if (!this.username || !this.email || !this.password || this.password !== this.confirmPassword || !this.validEmail) {
        this.error = true;
        return;
      }

      // 创建注册请求数据
      const registerData = {
        userName: this.username,
        email: this.email,
        passWord: this.password,
        name: this.username // 这里可以根据需要修改，假设姓名为用户名
      };

      // 发送注册请求
      axios.post('/api/user/register', registerData)
        .then(response => {
          if (response.data === "注册成功") {
            alert(`注册成功，${this.username}！`);
            this.$router.push('/login');
          } else {
            alert(response.data); // 显示错误信息
          }
        })
        .catch(error => {
          console.error("注册失败", error);
          alert("注册请求失败，请稍后再试");
        });
    },

    validateEmail(email) {
      const regex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/;
      return regex.test(email);
    }
  }
};
</script>

<style scoped>
.register-wrapper {
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%,-50%);
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  background: #f5f5f5;
}

.register-container {
  width: 300px;
  padding: 40px;
  background: #fff;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  border-radius: 12px;
}

.register-container h2 {
  text-align: center;
  margin-bottom: 20px;
}

.register-container input {
  width: 100%;
  padding: 10px;
  margin: 10px 0;
  border: 1px solid #ccc;
  border-radius: 8px;
}

.register-container button {
  width: 100%;
  padding: 10px;
  background-color: #409eff;
  color: white;
  border: none;
  border-radius: 8px;
  cursor: pointer;
}

.register-container button:hover {
  background-color: #66b1ff;
}

.error-message {
  color: red;
  font-size: 13px;
  margin-top: -8px;
  margin-bottom: 10px;
}
</style>
