<template>
<div class="login-wrapper">
		<input type="text"  class="input" autofocus="true" placeholder="请输入用户名" v-model="user.username">
		<input type="password" class="input" placeholder="请输入密码" v-model="user.password">
		<text class="button" @click="login">登录</text>
		<text class="addon" @click="findUsername">找回用户名</text>
    <Alert ref="alert"></Alert>
</div>
</template>
<script>
import { Alert } from '@/views/components'
export default {
  data() {
    return {
      user:
        {
          username: '',
          password: ''
        }
    }
  },
  methods: {
    login() {
      this.post('/login', this.user, (result) => {
        if (result && result.state == 'success') {
          this.redirect('Main')
          this.setData(this.KEY_TOKEN, result.data.token)
          this.setData(this.KEY_USER, JSON.stringify(result.data.user))
        } else if (result && result.state == 'error') {
          this.toast("用户名或密码错误")
        }
      })
    },
    findUsername() {
      if (this.user.username == '') {
        this.$refs.alert.show("在用户名一栏输入【身份证号】点击此按钮找回用户名")
      } else {
        this.get('/findUsernameByIdCard', { username: this.user.username }, (result) => {
          if (result && result.state == 'success') {
            this.$refs.alert.show("用户名是【" + result.data + "】")
          } else {
            this.$refs.alert.show(result.data)
          }
        })
      }
    }
  },
  components: {
    Alert
  }
}
</script>

<style scoped>
.login-wrapper {
  margin-left: 50px;
  margin-top: 80px;
}
.input {
  width: 650px;
  height: 100px;
  padding-left: 20px;
  padding-right: 20px;
  font-size: 38px;
  border-bottom-width: 2px;
  margin-top: 40px;
  border-bottom-color: #00bfff;
}
.button {
  width: 650px;
  height: 80px;
  margin-top: 80px;
  text-align: center;
  line-height: 80px;
  background-color: #00bfff;
}
.button:active {
  background-color: #0190c0;
}
.addon {
  font-size: 28px;
  margin-top: 26px;
  color: rgb(108, 108, 108);
}
</style>

