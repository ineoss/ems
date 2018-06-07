/*global Vue*/

/* weex initialized here, please do not move this line */
const router = require('../router')
const App = require('@/views/borrow/BorrowDetail.vue')
import mixin from '@/mixin/index'
/* eslint-disable no-new */
Vue.mixin(mixin)
new Vue(Vue.util.extend({ el: '#root', router }, App));
router.push('/');

