<!--    设备报废    -->
<template>
<div class="giveup-wrapper">
	<mini-bar title="设备报废" back="true" scan="true" @scanResult="scan"></mini-bar>
  <div class="main">
    <div class="row">
			<text class="label" >资产编号：</text>
			<input class="input" type="text" v-model="giveup.assetNo" autofocus="true">
			<text class="required">*</text>
		</div>
    
    <div class="row">
			<text class="label">是否回收：</text>
			<div class="gender-box" @click="gSelect(1)">
				<text class="g-text" :class="[gActive(1)]">是</text>
				<text class="iconfont g-icon" :class="[gActive(1)]" v-if="this.r==1">&#xe6ad;</text>
			</div>
    	<div class="gender-box" @click="gSelect(0)">
				<text class="g-text" :class="[gActive(0)]">否</text>
				<text class="iconfont g-icon" :class="[gActive(0)]" v-if="this.r==0">&#xe6ad;</text>
			</div>
		</div>
    <div class="row" v-if="r==1">
			<text class="label">回收价格：</text>
			<input class="input" type="number" v-model="giveup.returnPrice">
      <text style="height:80px;line-height:80px">元</text>
			<text class="required">*</text>
		</div>
    <div class="row">
			<text class="label">报废说明：</text>
			<textarea class="textarea" rows="4" v-model="giveup.mark"></textarea>
			<text class="required">*</text>
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
    giveup: {
      assetNo: '',
      mark: '',
      handler: '',
      returnPrice: ''
    },
    r: 1
  }),
  methods: {
    scan(e) {
      this.giveup.assetNo = e.assetNo
    },
    submit() {
      if (this.checkFormValue(this.giveup.assetNo, '【资产编号】不能为空')
        && this.checkFormValue(this.giveup.mark, '【报废说明】不能为空')
        && ((this.r == 1 && this.checkFormValue(this.giveup.returnPrice, "【回收价格】不能为空") || (this.r == 0)))) {
        this.post("/addGiveup", this.giveup, (result) => {
          if (result && result.state == 'success') {
            this.$refs.alert.show("设备报废成功")
            this.giveup.assetNo = ''
            this.giveup.mark = ''
            this.giveup.returnPrice = ''
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
      this.r = val
    },
    gActive(key) {
      return (this.r == key) ? 'g-active' : ''
    }
  },
  created() {
    this.getData(this.KEY_USER, (res) => {
      if (res) {
        let user = JSON.parse(res)
        this.giveup.handler = user.id
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
.giveup-wrapper {
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


