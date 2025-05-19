import Vue from 'vue'
import Vuex from 'vuex'
import snackbar from './snackbar'
import account from "./account"

Vue.use(Vuex)

const store = new Vuex.Store({
    state: {
        barColor: 'rgba(0, 0, 0, .8), rgba(0, 0, 0, .8)',
        barImage: '/003.png',
        drawer: null,
    },
    modules: {
        snackbar,
        account,
    }
    ,actions: {

    },
})

export default store