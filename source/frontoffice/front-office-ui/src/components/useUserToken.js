import {useSelector} from "react-redux";

function useUserToken() {
	return useSelector(store => store.auth.user.token);
}