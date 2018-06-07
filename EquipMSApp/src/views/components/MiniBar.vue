<template>
  <div class="minibar-wrapper">
	<text class="narrow iconfont" @click="goBack" v-if="showBack">&#xe697;</text>
	<text class="text">{{title}}</text>
  <text class="scan iconfont" @click="goScan" v-if="showScan">&#xe6dd;</text>
  </div>
</template>
<script>
export default {
  props: {
    title: '',
    back: false,
    scan: false
  },
  computed: {
    showBack() {
      return this.back
    },
    showScan() {
      return this.scan
    }
  },
  methods: {
    goBack() {
      this.toBack()
    },
    goScan() {
      this.getModule('event').scan((data) => {
        try {
          data = JSON.parse(data)
          if (data.id && data.assetNo) {
            this.$emit('scanResult', data)
          } else {
            this.toast('二维码格式不正确')
          }
        } catch (e) {
          this.toast('二维码格式不正确')
        }

      })

    }
  }
}
</script>
<style scoped>
.iconfont {
  font-family: iconfont;
}
.minibar-wrapper {
  position: absolute;
  height: 100px;
  top: 0;
  left: 0;
  right: 0;
  border-bottom-width: 3px;
  border-bottom-color: rgba(128, 128, 128, 0.4);
}
.narrow {
  position: absolute;
  left: 20px;
  font-size: 46px;
  font-weight: bold;
  color: #3c3c3ccc;
  width: 80px;
  height: 80px;
  line-height: 80px;
  text-align: center;
  border-radius: 40px;
  margin-top: 10px;
}
.narrow:active {
  background-color: rgba(192, 192, 192, 0.4);
}
.text {
  text-align: center;
  line-height: 100px;
  position: absolute;
  width: 550px;
  right: 100px;
  left: 100px;
}
.scan {
  position: absolute;
  right: 20px;
  font-size: 46px;
  font-weight: bold;
  color: #3c3c3ccc;
  width: 80px;
  height: 80px;
  line-height: 80px;
  text-align: center;
  margin-top: 10px;
}
</style>

