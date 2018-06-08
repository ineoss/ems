<template>
<div class="outstock-wrapper">
	<mini-bar title="设备出库" back="true" scan="true" @scanResult="scan"></mini-bar>
  <div class="main">
    <div class="row">
			<text class="label" >资产编号：</text>
			<input class="input" type="text" v-model="outstock.assetNo" autofocus="true">
			<text class="required">*</text>
		</div>
    <div class="row">
			<text class="label">存放地点：</text>
			<input class="input" type="text" v-model="outstock.pos">
      <text class="required">*</text>
		</div>
    <div class="row">
			<text class="label">领取部门：</text>
			<button @click="showSelectList('deptlist')" class="input" style="justify-content:center"><text>{{deptname}}</text></button>
      <text class="required">*</text>
		</div>
    <div class="row">
			<text class="label">备注：</text>
			<textarea class="textarea" rows="6" v-model="outstock.mark"></textarea>
		</div>
    <button @click="submit" class="button"><text class="button-text">确认</text></button>
  </div>
  <list-select :list="deptList" title="选择部门" valStr="deptname" ref="deptlist" @listSelected="listSelected"></list-select>
  <alert ref="alert"></alert>
</div>
</template>
<script>
import { MiniBar, Alert, ListSelect } from '@/views/components'
export default {
  data: () => ({
    outstock: {
      assetNo: '',
      dept: '',
      mark: '',
      handler: '',
      pos: ''
    },
    deptList: {},
    deptname: ''
  }),
  methods: {
    scan(e) {
      this.outstock.assetNo = e.assetNo
    },
    submit() {
      if (this.checkFormValue(this.outstock.assetNo, '请输入【资产编号】')
        && this.checkFormValue(this.outstock.dept, '请选择【领取部门】')
        && this.checkFormValue(this.outstock.pos, '请输入【存放地点】')) {
        this.post("/outStock", this.outstock, (result) => {
          if (result && result.state == 'success') {
            this.$refs.alert.show("出库成功")
            this.outstock.assetNo = ''
            this.outstock.dept = ''
            this.deptname = ''
            this.outstock.mark = ''
            this.outstock.pos = ''
          } else if (result && result.state == 'fail') {
            this.$refs.alert.show(result.data)
          } else {
            this.$refs.alert.show("出库失败")
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
    listSelected(e) {
      this.outstock.dept = e.obj.id
      this.deptname = e.obj.deptname
    },
    showSelectList(key) {
      this.select_flag = key
      this.$refs[key].show()
    },
  },
  created() {
    this.getData(this.KEY_DEPTLIST, (res) => {
      if (res) {
        this.deptList = JSON.parse(res)
      }
    })
    this.getData(this.KEY_USER, (res) => {
      if (res) {
        let user = JSON.parse(res)
        this.outstock.handler = user.id
      }
    })
  },
  components: {
    MiniBar,
    Alert,
    ListSelect
  }
}
</script>
<style scoped>
.iconfont {
  font-family: iconfont;
}
.outstock-wrapper {
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
  margin-left: 195px;
  margin-top: 100px;
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
</style>


