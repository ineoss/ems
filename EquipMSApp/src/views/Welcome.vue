<template>
<div class="welcome-wrapper">
  <img class="welcome-img" :src="img">
</div>
  
</template>
<script>

export default {
  data: () => ({
    img: ''
  }),
  created() {
    this.img = this.getImage('welcome.png')
    //获取部门列表
    this.get('/getDepartmentList', null, (result) => {
      if (result && result.state == 'success') {
        this.setData(this.KEY_DEPTLIST, JSON.stringify(result.data))
      }
    })
    //获取角色列表
    this.get('/getRoleList', null, (result) => {
      if (result && result.state == 'success') {
        this.setData(this.KEY_ROLELIST, JSON.stringify(result.data))
      }
    })
    //获取设备分类
    this.get('/getEquipmentType', null, (result) => {
      if (result && result.state == 'success') {
        this.setData(this.KEY_EQUIPTYPE, JSON.stringify(result.data))
      }
    })
    //获取设备状态
    this.get('/getEquipmentState', null, (result) => {
      if (result && result.state == 'success') {
        this.setData(this.KEY_EQUIPSTATE, JSON.stringify(result.data))
      }
    })
    //获取生产厂家
    this.get('/getFactoryList', null, (result) => {
      if (result && result.state == 'success') {
        this.setData(this.KEY_FACTORYLIST, JSON.stringify(result.data))
      }
    })
    setTimeout(() => {
      this.getModule('storage').getItem('ems_token', (res) => {
        if (res.result == 'success') {
          this.get('/tokenLogin', { token: res.data }, (result) => {
            if (result && result.state == 'success') {
              this.setData(this.KEY_USER, JSON.stringify(result.data))
              this.redirect("Main")
            } else if (result && result.state == 'error') {
              this.redirect("Login")
            } else { }
          })
        } else {
          this.redirect('Login')
        }
      })
    }, 500);

  }

}
</script>
<style scoped>
.welcome-wrapper {
  height: 1334px;
  width: 750px;
  background-color: #0083ce;
}
.welcome-img {
  height: 1334px;
  width: 750px;
}
</style>


