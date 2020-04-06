import React, {useEffect} from "react";
import FacebookLogin from 'react-facebook-login/dist/facebook-login-render-props';
import GoogleLogin from 'react-google-login';
import {Button, Form, Input} from "antd";
import Icon from "../../assets/icon.png";
import {familySetData} from "../../store/family/actions";
import ServiceAuth from "../../services/ServiceAuth";
import {authSetUser} from "../../store/auth/actions";
import ServiceFamily from "../../services/ServiceFamily";
import {useDispatch, useSelector} from "react-redux";
import {useHistory} from "react-router-dom";
import {appConfigSetMessage} from "../../store/appConfig/actions";


export default () => {
	const history  = useHistory();
	const [form]   = Form.useForm();
	const dispatch = useDispatch();
	const user     = useSelector(store => store.auth.user);
	const family   = useSelector(store => store.family);
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
	
	
	const onFinish = values => {
		ServiceAuth.loginEmail(values,
			(result) => {
				localStorage.setItem('token', result.token);
				dispatch(authSetUser({...result, logged: true}));
				ServiceFamily.getFamily(family => {
					dispatch(familySetData({
						...family,
						fetched: true
					}));
					history.push("/dashboard");
				});
			},
			(data) => {
				alert(data.detail);
				dispatch(appConfigSetMessage({text: data.detail}));
			});
	};
	
	const onFinishFailed = errorInfo => {
		console.log('Failed:', errorInfo);
	};
	
	return (<div>
			<div style={{display: "flex", flexDirection: "column", justifyContent: "center"}}>
				<img src={Icon} style={{height: 169, width: 120, margin: 'auto'}}/>
				<Form
					form={form}
					layout='vertical'
					onFinish={onFinish}
					onFinishFailed={onFinishFailed}
				>
					<Form.Item label="Nombre de usuario"
										 name="username"
										 rules={[{required: true, message: 'Ingresa nombre de usuario'}]}>
						<Input placeholder="Introduce en nombre de usuario"/>
					</Form.Item>
					<Form.Item label="Password"
										 name="password"
										 rules={[{required: true, message: 'Ingresa tu password'}]}>
						<Input.Password placeholder="Introduce tu password"/>
					</Form.Item>
					<Form.Item>
						<div style={{display: "flex", flexDirection: "row"}}>
							<Button type="primary" htmlType="submit"
											style={{width: '100%', marginBottom: 8, marginLeft: 8}}>Guardar</Button>
						</div>
					</Form.Item>
				</Form>
				<FacebookLogin
					appId="1088597931155576"
					autoLoad={false}
					fields="name,email,picture"
					render={renderProps => (
						<Button type="default" onClick={renderProps.onClick} style={{marginBottom: 8, display: 'none'}}>
							<p style={{color: "blue"}}>Iniciar con Facebook</p></Button>
					)}
				
				/>
				<GoogleLogin
					clientId="658977310896-knrl3gka66fldh83dao2rhgbblmd4un9.apps.googleusercontent.com"
					buttonText="Login"
					onSuccess={() => {
						console.log("success");
					}}
					onFailure={() => {
						console.log("fail");
					}}
					cookiePolicy={'single_host_origin'}
					render={renderProps => (
						<Button type="default" onClick={renderProps.onClick} style={{display: 'none'}}>
							<p style={{color: "red"}}>Iniciar con Google</p>
						</Button>
					)}
				
				/>
			
			
			</div>
		
		</div>
	);
}