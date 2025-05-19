module.exports = {
  lintOnSave: undefined,   
publicPath: './',   
outputDir: undefined,   
assetsDir: undefined,   
runtimeCompiler: undefined,   
productionSourceMap: undefined,   
parallel: undefined,   
css: undefined ,

  lintOnSave: false,
  devServer: {
    disableHostCheck: true,
    host: '0.0.0.0',
    port: 8081,
    proxy: { // 设置代理
      '/': {
        target: process.env.VUE_APP_API_BASE_URL,
        changeOrigin: true,
        pathRewrite: {
          '^/': ''
        }
      }
    },
  },

  transpileDependencies: ['vuetify', 'vue-echarts',
    'resize-detector'],

  pluginOptions: {
    i18n: {
      locale: 'en',
      fallbackLocale: 'en',
      localeDir: 'locales',
      enableInSFC: false,
    },
  },
  configureWebpack: {
    devtool: 'source-map'
  }
}
