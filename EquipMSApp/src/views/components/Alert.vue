<template>
  	<wxc-dialog 
	  :show="v" 
	  :content="content" 
	  :single="single"
	  @wxcDialogConfirmBtnClicked="dialogConfirmBtnClick" 
	  @wxcDialogCancelBtnClicked="dialogCancelBtnClick">
	</wxc-dialog>
</template>
<script>
import { WxcDialog } from 'weex-ui'
export default {
	props: {
		single: { type: Boolean, default: true }
	},
	data: () => ({
		v: false,
		content: ''
	}),
	methods: {
		show(msg) {
			this.content = msg
			this.v = true
		},
		dialogConfirmBtnClick() {
			this.v = false
			this.content = ''
			this.$emit('dialogConfirmBtnClick')
		},
		dialogCancelBtnClick() {
			this.v = false
			this.content = ''
		},
	},
	components: {
		WxcDialog
	}
}
</script>

