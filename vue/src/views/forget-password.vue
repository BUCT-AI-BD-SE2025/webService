<template>
  <div class="page-center">
    <div class="reset-container">
      <h2>忘记密码</h2>

      <input type="email" v-model="email" placeholder="请输入邮箱" />
      <div class="verification-row">
        <input type="text" v-model="code" placeholder="验证码" />
        <button class="code-btn" @click="sendCode">发送验证码</button>
      </div>

      <input type="password" v-model="newPassword" placeholder="新密码" />
      <input type="password" v-model="confirmPassword" placeholder="确认新密码" />

      <button class="reset-btn" @click="resetPassword">重置密码</button>
    </div>
  </div>
</template>
<script>
import axios from 'axios';

export default {
  data() {
    return {
      email: '',
      code: '',
      newPassword: '',
      confirmPassword: '',
    };
  },
  methods: {
    sendCode() {
      if (!this.email) {
        alert('请输入邮箱！');
        return;
      }

      axios.post('http://localhost:9099/forget/code', {
        email: this.email
      }).then(res => {
        alert(res.data.message || '验证码已发送，请查收');
      }).catch(err => {
        alert(err.response?.data?.message || '验证码发送失败，请重试');
      });
    },
    resetPassword() {
      if (!this.code || !this.newPassword || !this.confirmPassword) {
        alert('请填写所有字段！');
        return;
      }
      if (this.newPassword !== this.confirmPassword) {
        alert('两次密码输入不一致！');
        return;
      }

      axios.post('http://localhost:9099/forget/reset', {
        email: this.email,
        code: this.code,
        password: this.newPassword
      }).then(res => {
        alert(res.data.message || '密码重置成功，请返回登录');
        this.$router.push('/login');
      }).catch(err => {
        alert(err.response?.data?.message || '重置失败，请重试');
      });
    }
  }
};
</script>

<style scoped>
.page-center {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-color: #f0f2f5;
}

.reset-container {
  background: #ffffff;
  padding: 40px;
  width: 350px;
  border-radius: 16px;
  box-shadow: 0 6px 20px rgba(0, 0, 0, 0.1);
}

.reset-container h2 {
  text-align: center;
  margin-bottom: 24px;
  color: #333;
}

.reset-container input {
  width: 100%;
  padding: 10px 12px;
  margin: 10px 0;
  border: 1px solid #ccc;
  border-radius: 8px;
  box-sizing: border-box;
}

.verification-row {
  display: flex;
  gap: 8px;
  margin: 10px 0;
}

.verification-row input {
  flex: 1;
}

.code-btn {
  background-color: #409eff;
  border: none;
  color: white;
  padding: 0 16px;
  border-radius: 8px;
  cursor: pointer;
  transition: background-color 0.2s;
}

.code-btn:hover {
  background-color: #66b1ff;
}

.reset-btn {
  width: 100%;
  padding: 10px;
  background-color: #409eff;
  color: white;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  margin-top: 20px;
}

.reset-btn:hover {
  background-color: #66b1ff;
}
</style>
