import React, {useEffect} from 'react';
import {useDispatch, useSelector} from "react-redux";
import {authSetUser} from "../../store/auth/actions";
import {useHistory} from "react-router-dom";
import ServiceFamily from "../../services/ServiceFamily";
import {familySetData} from "../../store/family/actions";

export default () => {
	const history  = useHistory();
	const dispatch = useDispatch();
	const user     = useSelector(store => store.auth.user);
	const family   = useSelector(store => store.family);
	
	useEffect(() => {
		const token = localStorage.getItem('token');
		if (token !== null && token !== undefined && token !== "") {
			dispatch(authSetUser({token: token, logged: true}));
			ServiceFamily.getFamily(
				family => {
					dispatch(familySetData({
						...family,
						fetched: true
					}));
				},
				() => {
					console.log("no valid token");
					history.push('/login');
				}
			);
		} else {
			history.push('/login');
		}
		// ServiceAuth.loginEmail({
		// 		username: 'admin',
		// 		password: 'admin'
		// 	},
		// 	(result) => {
		// 		// user valid
		// 		localStorage.setItem('token', result.token);
		// 		dispatch(authSetUser({...result, logged: true}));
		// 		// getting family information
		// 		ServiceFamily.getFamily(family => {
		// 			dispatch(familySetData({
		// 				...family,
		// 				fetched: true
		// 			}));
		// 		});
		// 	});
	}, []);
	
	useEffect(() => {
		if (user.logged) {
			if (family.fetched) {
				if (family.id > 0) {
					history.push("/dashboard");
				} else {
					history.push("/family-data");
				}
			} else {
				//wait until fetch
			}
		} else {
			//TODO goto login page
		}
	}, [user, family]);
	return <div>Cargando..</div>;
}