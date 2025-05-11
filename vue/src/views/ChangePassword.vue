<template>
  <div class="change-password-page">
    <div class="form-card">
      <h2>修改密码</h2>

      <div class="form-group">
        <label for="oldPassword">旧密码</label>
        <input type="password" v-model="oldPassword" id="oldPassword" />
      </div>

      <div class="form-group">
        <label for="newPassword">新密码</label>
        <input type="password" v-model="newPassword" id="newPassword" />
      </div>

      <div class="form-group">
        <label for="confirmPassword">确认新密码</label>
        <input type="password" v-model="confirmPassword" id="confirmPassword" />
      </div>

      <div class="form-actions">
        <button @click="submit">提交</button>
        <button class="cancel" @click="goBack">取消</button>
      </div>

      <p class="error-message" v-if="errorMessage">{{ errorMessage }}</p>
      <p class="success-message" v-if="successMessage">{{ successMessage }}</p>
    </div>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  data() {
    return {
      oldPassword: '',
      newPassword: '',
      confirmPassword: '',
      errorMessage: '',
      successMessage: ''
    }
  },
  methods: {
    async submit() {
      this.errorMessage = ''
      this.successMessage = ''

      if (!this.oldPassword || !this.newPassword || !this.confirmPassword) {
        this.errorMessage = '所有字段均为必填'
        return
      }

      if (this.oldPassword === this.newPassword) {
        this.errorMessage = '新密码不能和旧密码相同'
        return
      }

      if (this.newPassword !== this.confirmPassword) {
        this.errorMessage = '新密码与确认密码不一致'
        return
      }

      try {
        const response = await axios.post('/api/user/change-password', {
          oldPassword: this.oldPassword,
          newPassword: this.newPassword
        })

        if (response.data.code === 200) {
          this.successMessage = '密码修改成功，即将返回...'
          setTimeout(() => {
            this.$router.push({ name: 'profile' })
          }, 2000)
        } else {
          this.errorMessage = response.data.msg || '密码修改失败'
        }
      } catch (error) {
        this.errorMessage = '请求失败，请稍后再试'
        console.error(error)
      }
    },
    goBack() {
      this.$router.back()
    }
  }
}
</script>

<style scoped>
.change-password-page {
  min-height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: #f5f5f5;
}

.form-card {
  background: white;
  padding: 30px;
  border-radius: 10px;
  width: 400px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.2);
}

.form-card h2 {
  text-align: center;
  margin-bottom: 20px;
}

.form-group {
  margin-bottom: 15px;
}

label {
  display: block;
  margin-bottom: 5px;
  font-weight: bold;
}

input[type="password"] {
  width: 100%;
  padding: 8px;
  border: 1px solid #ccc;
  border-radius: 5px;
}

.form-actions {
  display: flex;
  justify-content: space-between;
  margin-top: 20px;
}

button {
  padding: 8px 16px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

button.cancel {
  background-color: #ccc;
}

button:not(.cancel) {
  background-color: #1e90ff;
  color: white;
}

.error-message {
  color: red;
  margin-top: 10px;
  text-align: center;
}

.success-message {
  color: green;
  margin-top: 10px;
  text-align: center;
}
</style>
