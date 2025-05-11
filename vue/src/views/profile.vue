<template>
  <div class="profile-page">
    <header class="profile-header">
      <h1>用户个人信息</h1>
      <button class="back-button" @click="goBack">返回</button>
    </header>

    <main class="profile-content">
      <div class="profile-card" v-if="!isLoading">
        <img :src="user.avatar || defaultAvatar" alt="用户头像" class="avatar" />

        <div class="form-item">
          <label class="form-label">姓名：</label>
          <input v-model="user.name" class="form-input" />
        </div>

        <div class="form-item">
          <label class="form-label">邮箱：</label>
          <input v-model="user.email" class="form-input" />
        </div>

        <p><strong>注册时间：</strong>{{ user.registerDate }}</p>

        <button class="save-button" @click="saveChanges">修改信息</button>
        <button @click="changePassword">修改密码</button>
        <button @click="switchAccount">切换账号</button>
      </div>

      <div v-if="isLoading" class="loading-spinner">加载中...</div>
    </main>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router'
import { ref, onMounted } from 'vue'
import axios from 'axios'

const router = useRouter()
const user = ref({
  name: '',
  email: '',
  registerDate: '',
  avatar: ''  // 用户头像字段
})
const defaultAvatar = 'https://via.placeholder.com/120'  // 默认头像地址
const isLoading = ref(true)  // 加载状态

// 获取用户信息
const fetchUserData = async () => {
  try {
    const token = localStorage.getItem('token')  // 假设 token 存储在 localStorage 中
    const response = await axios.get('http://localhost:9099/api/user/profile', {
      headers: {
        'Authorization': `Bearer ${token}`  // 加入 Authorization 头部
      }
    })
    if (response.data.code === 200) {
      user.value = response.data.data
    } else {
      console.error('获取用户信息失败:', response.data.msg)
      alert(response.data.msg)  // 可以替换为更友好的 UI 提示
    }
  } catch (error) {
    console.error('请求失败:', error)
    alert('请求失败，请稍后重试')
  } finally {
    isLoading.value = false  // 请求结束后关闭加载状态
  }
}
const saveChanges = async () => {
  try {
    const token = localStorage.getItem('token')
    const response = await axios.post('http://localhost:9099/api/user/profile/update', {
      name: user.value.name,
      email: user.value.email
    }, {
      headers: {
        Authorization: `Bearer ${token}`
      }
    })

    if (response.data.code === 200) {
      alert('修改成功')
    } else {
      alert(response.data.msg || '保存失败')
    }
  } catch (error) {
    console.error('保存失败:', error)
    alert('保存失败，请稍后重试')
  }
}


// 返回上一页
function goBack() {
  router.back()
}
function changePassword() {
  router.push({ name: 'ChangePassword' })
}

function switchAccount() {
  router.push({ name: 'login' })
}

// 在组件加载时获取用户数据
onMounted(fetchUserData)
</script>

<style scoped>
.profile-page {
  width: 100%;
  min-height: 100vh;
  background-color: white;
  display: flex;
  flex-direction: column;
}

.profile-header {
  background-color: #87cefa;
  padding: 20px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.profile-header h1 {
  margin: 0;
  font-size: 24px;
}

.back-button {
  background-color: #1e90ff;
  color: white;
  border: none;
  padding: 8px 16px;
  border-radius: 5px;
  cursor: pointer;
  font-size: 14px;
}

.profile-content {
  flex: 1;
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 20px;
}

.profile-card {
  background-color: #f0f0f0;
  padding: 30px;
  border-radius: 10px;
  text-align: center;
  width: 300px;
}

.avatar {
  width: 120px;
  height: 120px;
  border-radius: 50%;
  object-fit: cover;
  margin-bottom: 20px;
}

/* 加载中样式 */
.loading-spinner {
  font-size: 18px;
  color: #1e90ff;
  font-weight: bold;
}
</style>
