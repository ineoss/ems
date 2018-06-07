<template>
<div class="me-wrapper">
  <mini-bar title="我的"></mini-bar>
  <div class="main">
    <div class="info-box">
      <img class="avatar" :src="avatar">
      <div>
        <text>{{user.truename}}</text>
        <text>{{user.deptname}}</text>
        <text>{{user.rolename}}</text>
      </div>
    </div>
    <div class="util">
        <wxc-cell :has-arrow="true"
                  :has-top-border="false"
                  :auto-accessible="true"
                  @wxcCellClicked="myApply">
          <text  slot="title">我的借用申请</text>
        </wxc-cell>
    </div>
    <div class="logout" @click="logout">
        <text class="logout-text">退出登录</text>
    </div>
  </div>
</div>
</template>
<script>
import config from '@/config'
import MiniBar from '@/views/components/MiniBar'
import { WxcCell } from 'weex-ui'
let server_ip = config.server_ip

export default {
  data: () => ({
    user: { avatar: '/avatar/default_avatar.jpg' },
  }),
  methods: {
    myApply(){
      this.jumpTo('MyBorrowApply')
    },
    logout() {
      this.getData(this.KEY_TOKEN, (res) => {
        if (res) {
          this.get('/logout', { token: res }, (result) => {
          })
        }
      })

      this.removeData(this.KEY_TOKEN)
      this.removeData(this.KEY_USER)
      this.redirect('Login')
    }
  },
  computed: {
    avatar() {
      return server_ip + this.user.avatar
    }
  },
  created() {
    this.getData(this.KEY_USER, (res) => {
      if (res) {
        this.user = JSON.parse(res)
      }
    })
  },
  components: {
    MiniBar,
    WxcCell
  }
}
</script>
<style scoped>
.me-wrapper {
  position: absolute;
  bottom: 0;
  top: 0;
  left: 0;
  right: 0;
  background-color: #f0f0f0;
}
.main {
  position: absolute;
  bottom: 0;
  top: 100px;
  left: 0;
  right: 0;
}
.info-box {
  width: 750px;
  height: 180px;
  margin-bottom: 20px;
  margin-top: 20px;
  padding-left: 40px;
  flex-direction: row;
  align-items: center;
  background-color: #ffffff;
}
.avatar {
  width: 160px;
  height: 160px;
  margin-right: 60px;
  background-color: darkgoldenrod;
}

.util {
  width: 750px;
  margin-top:40px;
  background-color: #ffffff;
}

.logout {
  background-color: rgb(252, 252, 252);
  position: absolute;
  left: 0;
  right: 0;
  bottom: 40px;
  align-items: center;
}
.logout-text {
  color: #ff0000;
  background-color: rgb(252, 252, 252);
  height: 100px;
  line-height: 100px;
}
.user-info {
  width: 750px;
  height: 80px;
  flex-direction: row;
  justify-content: center;
  align-items: center;
  background-color: #ffffff;
  margin-bottom: 20px;
}
.info-text {
  text-align: center;
  line-height: 60px;
  height: 60px;
}
</style>