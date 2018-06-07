<template>
  <div>
    <router-view/>
  </div>
</template>
<script>
export default {
  created() {
    weex.requireModule('dom').addRule('fontFace', {
      'fontFamily': "iconfont",
      'src': "url('http://at.alicdn.com/t/font_574371_s5c6v0vdrh08uxr.ttf')"
    });
  },
}
</script>
<style>

</style>



