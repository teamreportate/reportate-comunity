import React, {useEffect} from 'react';
import ServiceAuth from "../../services/ServiceAuth";
import {useDispatch, useSelector} from "react-redux";
import {authSetUser} from "../../store/auth/actions";
import {familySetData} from "../../store/family/actions";
import {useHistory} from "react-router-dom";
import ServiceFamily from "../../services/ServiceFamily";

export default () => {
	const history  = useHistory();
	const dispatch = useDispatch();
	const user     = useSelector(store => store.auth.user);
	const family   = useSelector(store => store.family);
	
	useEffect(() => {
		localStorage.setItem('token', "");
		ServiceAuth.loginEmail({
				username: 'admin',
				password: 'admin'
			},
			(result) => {
				// user valid
				localStorage.setItem('token', result.token);
				console.log(localStorage.getItem('token'));
				dispatch(authSetUser({...result, logged: true}));
				// getting family information
				ServiceFamily.getFamily(family => {
					dispatch(familySetData({
						...family,
						fetched: true
					}));
				});
			});
	}, []);
	
	useEffect(() => {
		if (user.logged) {
			if (family.fetched) {
				if (family.id === "") {
					history.push("/family-data");
				} else {
					history.push("/dashboard");
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