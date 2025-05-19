import Vue from 'vue'
import Vuetify from 'vuetify/lib'
import i18n from '@/i18n'
import '@/sass/overrides.sass'
// vuetify提供的颜色变量
import colors from 'vuetify/es5/util/colors'

Vue.use(Vuetify)

const theme = {
  primary: '#4CAF50',
  secondary: '#4b89dc',
  accent: '#232f34',
  info: '#00CAE3',
  divder: '#121212',
  error: colors.red.base
}

export default new Vuetify({
  lang: {
    t: (key, ...params) => i18n.t(key, params),
  },
  theme: {
    themes: {
      dark: theme,
      light: theme,
    },
  },
})
