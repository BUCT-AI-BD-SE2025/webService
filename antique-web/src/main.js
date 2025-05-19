// =========================================================
// * Vuetify Material Dashboard - v2.1.0
// =========================================================
//
// * Product Page: https://www.creative-tim.com/product/vuetify-material-dashboard
// * Copyright 2019 Creative Tim (https://www.creative-tim.com)
//
// * Coded by Creative Tim
//
// =========================================================
//
// * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import axios from 'axios'
import ECharts from 'vue-echarts'
import 'echarts/lib/chart/bar'
import 'echarts/lib/chart/line'
import 'echarts/lib/component/tooltip'
import 'echarts/lib/component/title'
import 'echarts/lib/component/legend'



import './plugins/base'
import './plugins/chartist'
import './plugins/vee-validate'
import vuetify from './plugins/vuetify'
import i18n from './i18n'

import request from './utils/request'
import db from './utils/localstorage'
import Message from './message/index.js'
import '@mdi/font/css/materialdesignicons.css'


Vue.component('v-chart', ECharts )

Vue.prototype.$echarts = ECharts

Vue.use(db)

Vue.use({
  install(Vue){
    Vue.prototype.$db = db
  }
})

Vue.config.productionTip = false

Vue.prototype.$post = request.post
Vue.prototype.$postjson = request.postjson
Vue.prototype.$get = request.get
Vue.prototype.$put = request.put
Vue.prototype.$del = request.del
Vue.prototype.$export = request.export
Vue.prototype.$download = request.download
Vue.prototype.$upload = request.upload
Vue.prototype.$uploadAndDownload = request.uploadAndDownload

Vue.prototype.$message = Message

Vue.prototype.axios = axios

new Vue({
  router,
  store,
  vuetify,
  i18n,
  render: h => h(App),
}).$mount('#app')
