<template>
<div class="list-wrapper" ref="list">
	<mini-bar :title="title"></mini-bar>
	<list class="list">
    	<cell @click="listSelected(i)" class="cell" v-for="(item,i) in list" :key="i" :class="[i>0?'border':'']" >
            <text>{{list[i][valStr]}}</text>
        </cell>
    </list>
	<text @click="retrn" class="return">返回</text>
</div>
  
</template>
<script>
import MiniBar from '@/views/components/MiniBar'
export default {

	props: {
		title: { type: String, default: '请选择' },
		valStr: { type: String, default: 'value' },
		list: {}
	},
	methods: {
		show(status = true, callback = null) {
			this.getModule('animation').transition(this.$refs.list, {
				styles: {
					transform: `translateX(${status ? -750 : 750}px)`
				},
				duration: status ? 250 : 300, // ms
				timingFunction: status ? 'ease-in' : 'ease-out',
				delay: 0 // ms
			}, function () {
				callback && callback();
			});

		},
		retrn() {
			this.show(false);
		},
		listSelected(i) {
			this.show(false);
			let obj = this.list[i]
			this.$emit('listSelected', { obj });
		}
	},

	components: {
		MiniBar
	}
}
</script>
<style>
.list-wrapper {
  position: fixed;
  width: 750px;
  top: 0;
  left: 750px;
  right: 0;
  bottom: 0;
  background-color: #f2f3f4;
}
.list {
  position: absolute;
  width: 750px;
  top: 100px;
  left: 0;
  right: 0;
  bottom: 120px;
  border-bottom-width: 2px;
  border-bottom-color: rgba(128, 128, 128, 0.5);
}
.cell {
  height: 80px;
  padding-left: 60px;
  padding-right: 60px;
  justify-content: center;
}
.return {
  position: absolute;
  bottom: 40px;
  right: 100px;
  color: tomato;
}
.border {
  border-top-width: 1px;
  border-top-color: rgba(128, 128, 128, 0.5);
}
</style>


