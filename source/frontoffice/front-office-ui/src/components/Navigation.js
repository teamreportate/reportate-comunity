import React from "react";
import {Button, Layout, Menu, Popover} from 'antd';
import {FaRegSun} from "react-icons/fa";
import Icon from '../assets/icon-120.png';
import {useDispatch} from "react-redux";
import {authLogout} from "../store/auth/actions";

const {Header} = Layout;

export default () => {
	// let history    = useHistory();
	const dispatch = useDispatch();
	
	const logout  = () => {
		dispatch(authLogout());
		// history.push("/login");
	};
	const content = (
		<Menu style={{border: 'none'}}>
			<Menu.Item onClick={logout}
			>Cerrar sessión</Menu.Item>
		</Menu>
	);
	return (
		<Header className='primary' style={{
			position      : 'fixed',
			zIndex        : 1,
			width         : '100%',
			display       : 'flex',
			height        : 60,
			justifyContent: 'space-between',
			alignItems    : 'center',
			
		}}>
			<img src={Icon} alt="Reportate Logo" style={{width: 40, height: 40}}/>
			<h2 style={{margin: 0}}>Reportate</h2>
			<Popover placement="bottomRight" content={content} trigger="click">
				<Button type="link" size={'large'} style={{display: "flex"}}><FaRegSun/></Button>
			</Popover>
		</Header>
	);
}