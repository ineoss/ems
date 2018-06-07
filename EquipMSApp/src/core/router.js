import Welcome from '@/views/Welcome'
import Login from '@/views/Login'
import Main from '@/views/menu/Main'
import Home from '@/views/menu/Home'
import Menu from '@/views/menu/Menu'
import Me from '@/views/menu/Me'
import AddUser from '@/views/user/AddUser'
import UserList from '@/views/user/UserList'
import UserDetail from '@/views/user/UserDetail'
import ModifyUser from '@/views/user/ModifyUser'
import AddEquip from '@/views/equip/AddEquip'
import InStock from '@/views/equip/InStock'
import OutStock from '@/views/equip/OutStock'
import StockList from '@/views/equip/StockList'
import EquipList from '@/views/equip/EquipList'
import EquipDetail from '@/views/equip/EquipDetail'
import ModifyEquip from '@/views/equip/ModifyEquip'
import AddBuyPlan from '@/views/buy/AddBuyPlan'
import BuyPlanList from '@/views/buy/BuyPlanList'
import BuyPlanDetail from '@/views/buy/BuyPlanDetail'
import ModifyBuyPlan from '@/views/buy/ModifyBuyPlan'
import EndBuyPlan from '@/views/buy/EndBuyPlan'
import BuyHistory from '@/views/buy/BuyHistory'
import BuyHistoryDetail from '@/views/buy/BuyHistoryDetail'
import CanBorrowList from '@/views/borrow/CanBorrowList'
import BorrowAuditingList from '@/views/borrow/BorrowAuditingList'
import BorrowDetail from '@/views/borrow/BorrowDetail'
import BorrowedList from '@/views/borrow/BorrowedList'
import BorrowedDetail from '@/views/borrow/BorrowedDetail'
import MyBorrowApply from '@/views/borrow/MyBorrowApply'
import AddGiveup from '@/views/giveup/AddGiveup'
import GiveupList from '@/views/giveup/GiveupList'
import GiveupDetail from '@/views/giveup/GiveupDetail'
import AddRepaire from '@/views/repaire/AddRepaire'
import NeedOverhaul from '@/views/repaire/NeedOverhaul'
import RepaireHistory from '@/views/repaire/RepaireHistory'
import RepaireDetail from '@/views/repaire/RepaireDetail'
const routes = [
	{ path: '/', name: 'Welcome', component: Welcome },
	{ path: '/Main', name: 'Main', component: Main },
	{ path: '/Login', name: 'Login', component: Login },
	{ path: '/AddUser', name: 'AddUser', component: AddUser },
	{ path: '/Home', name: 'Home', component: Home },
	{ path: '/Menu', name: 'Menu', component: Menu },
	{ path: '/Me', name: 'Me', component: Me },
	{ path: '/AddEquip', name: 'AddEquip', component: AddEquip },
	{ path: '/InStock', name: 'InStock', component: InStock },
	{ path: '/OutStock', name: 'OutStock', component: OutStock },
	{ path: '/StockList', name: 'StockList', component: StockList },
	{ path: '/UserList', name: 'UserList', component: UserList },
	{ path: '/UserDetail', name: 'UserDetail', component: UserDetail },
	{ path: '/ModifyUser', name: 'ModifyUser', component: ModifyUser },
	{ path: '/EquipList', name: 'EquipList', component: EquipList },
	{ path: '/EquipDetail', name: 'EquipDetail', component: EquipDetail },
	{ path: '/ModifyEquip', name: 'ModifyEquip', component: ModifyEquip },
	{ path: '/AddBuyPlan', name: 'AddBuyPlan', component: AddBuyPlan },
	{ path: '/BuyPlanList', name: 'BuyPlanList', component: BuyPlanList },
	{ path: '/BuyPlanDetail', name: 'BuyPlanDetail', component: BuyPlanDetail },
	{ path: '/ModifyBuyPlan', name: 'ModifyBuyPlan', component: ModifyBuyPlan },
	{ path: '/EndBuyPlan', name: 'EndBuyPlan', component: EndBuyPlan },
	{ path: '/BuyHistory', name: 'BuyHistory', component: BuyHistory },
	{ path: '/BuyHistoryDetail', name: 'BuyHistoryDetail', component: BuyHistoryDetail },
	{ path: '/CanBorrowList', name: 'CanBorrowList', component: CanBorrowList },
	{ path: '/BorrowAuditingList', name: 'BorrowAuditingList', component: BorrowAuditingList },
	{ path: '/BorrowDetail', name: 'BorrowDetail', component: BorrowDetail },
	{ path: '/BorrowedList', name: 'BorrowedList', component: BorrowedList },
	{ path: '/BorrowedDetail', name: 'BorrowedDetail', component: BorrowedDetail },
	{ path: '/AddGiveup', name: 'AddGiveup', component: AddGiveup },
	{ path: '/GiveupList', name: 'GiveupList', component: GiveupList },
	{ path: '/GiveupDetail', name: 'GiveupDetail', component: GiveupDetail },
	{ path: '/AddRepaire', name: 'AddRepaire', component: AddRepaire },
	{ path: '/MyBorrowApply', name: 'MyBorrowApply', component: MyBorrowApply },
	{ path: '/NeedOverhaul', name: 'NeedOverhaul', component: NeedOverhaul },
	{ path: '/RepaireHistory', name: 'RepaireHistory', component: RepaireHistory },
	{ path: '/RepaireDetail', name: 'RepaireDetail', component: RepaireDetail },
]
export default routes