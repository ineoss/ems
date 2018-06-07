<!--   添加维修    -->
<template>
<div class="repaire-wrapper">
	<mini-bar title="添加维修信息" back="true" scan="true" @scanResult="scan"></mini-bar>
  <div class="main">
    <div class="row">
			<text class="label" >资产编号：</text>
			<input class="input" type="text" v-model="data.assetNo" autofocus>
			<text class="required">*</text>
		</div>
    <div class="row">
			<text class="label">维修说明：</text>
			<input class="input" type="text" v-model="data.mark">
			<text class="required">*</text>
		</div>
    <div class="row">
			<text class="label">维修价格：</text>
			<input class="input" type="number" v-model="data.price">
      <text style="height:80px;line-height:80px">元</text>
      <text class="required">*</text>
		</div>
    <div class="row">
			<text class="label">维修日期：</text>
			<input class="input" type="date" v-model="data.date">
      <text class="required">*</text>
		</div>
    <div class="row">
			<text class="label" >维修地点：</text>
			<input class="input" type="text" v-model="data.pos">
			<text class="required">*</text>
		</div>
    <div class="row">
			<text class="label">是否成功：</text>
			<div class="gender-box" @click="gSelect(1)">
				<text class="g-text" :class="[gActive(1)]">是</text>
				<text class="iconfont g-icon" :class="[gActive(1)]" v-if="this.data.success==1">&#xe6ad;</text>
			</div>
    	<div class="gender-box" @click="gSelect(0)">
				<text class="g-text" :class="[gActive(0)]">否</text>
				<text class="iconfont g-icon" :class="[gActive(0)]" v-if="this.data.success==0">&#xe6ad;</text>
			</div>
		</div>
    <button @click="submit" class="button"><text class="button-text">确认</text></button>
  </div>
  <alert ref="alert"></alert>
</div>
</template>
<script>
import { MiniBar, Alert } from '@/views/components'
export default {
  data: () => ({
    data: {
      assetNo: '',
      mark: '',
      price: '',
      pos: '',
      handler: '',
      success: 1,
      date: ''
    },
  }),
  methods: {
    scan(e) {
      this.data.assetNo = e.assetNo
    },
    submit() {
      if (this.checkFormValue(this.data.assetNo, '【资产编号】不能为空')
        && this.checkFormValue(this.data.mark, '【维修说明】不能为空')
        && this.checkFormValue(this.data.date, '【维修日期】不能为空')
        && this.checkFormValue(this.data.price, '【维修价格】不能为空')
        && this.checkFormValue(this.data.mark, '【维修地点】不能为空')) {
        this.post("/addRepaire", this.data, (result) => {
          if (result && result.state == 'success') {
            this.$refs.alert.show("添加成功")
            this.data.assetNo = ''
            this.data.mark = ''
            this.data.pos = ''
            this.data.date = ''
            this.data.price = ''
          } else if (result && result.state == 'fail') {
            this.$refs.alert.show(result.data)
          } else {
            this.$refs.alert.show("操作失败")
          }
        })
      }

    },
    checkFormValue(value, msg) {
      if (value == '') {
        this.$refs.alert.show(msg)
        return false
      }
      return true
    },
    gSelect(val) {
      this.data.success = val
    },
    gActive(key) {
      return (this.data.success == key) ? 'g-active' : ''
    }
  },
  created() {
    this.getData(this.KEY_USER, (res) => {
      if (res) {
        let user = JSON.parse(res)
        this.data.handler = user.id
      }
    })
  },
  components: {
    MiniBar,
    Alert
  }
}
</script>
<style scoped>
.iconfont {
  font-family: iconfont;
}
.repaire-wrapper {
  background-color: #f0f0f0;
  position: absolute;
  bottom: 0;
  left: 0;
  right: 0;
  top: 0;
}
.main {
  position: absolute;
  bottom: 0;
  left: 0;
  right: 0;
  top: 140px;
}
.row {
  flex-direction: row;
  background-color: #fff;
  padding-left: 60px;
  border-bottom-width: 1px;
  border-bottom-color: rgba(128, 128, 128, 0.5);
}
.label {
  height: 80px;
  width: 180px;
  text-align: center;
  line-height: 80px;
}
.input {
  height: 80px;
  width: 360px;
  padding-left: 20px;
  padding-right: 20px;
}
.textarea {
  width: 360px;
  border-width: 1px;
  border-color: cadetblue;
  border-radius: 4px;
  padding-left: 10px;
  padding-right: 10px;
  padding-top: 10px;
  padding-bottom: 10px;
}
.button {
  height: 80px;
  width: 360px;
  border-radius: 4px;
  background-color: orangered;
  justify-content: center;
  align-items: center;
  position: absolute;
  left: 195px;
  bottom: 200px;
}
.button-text {
  color: #ffffff;
  font-size: 36px;
  text-align: center;
}
.required {
  height: 80px;
  line-height: 80px;
  position: absolute;
  right: 60px;
  color: orangered;
}
.gender-box {
  flex-direction: row;
  justify-content: center;
  width: 160px;
  height: 80px;
}
.g-text {
  height: 80px;
  line-height: 80px;
}
.g-icon {
  font-size: 48px;
  height: 80px;
  line-height: 80px;
}
.g-active {
  color: orangered;
}
</style>


