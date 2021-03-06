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
import {FaFacebookF, FaGoogle} from "react-icons/fa";


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
	
	
	const onFinish      = values => {
		ServiceAuth.loginEmail(values,
			(result) => {
				localStorage.setItem('token', result.token);
				dispatch(authSetUser({...result, logged: true}));
				ServiceFamily.getFamily(family => {
					dispatch(familySetData({
						...family,
						fetched: true
					}));
					history.push("/");
				});
			},
			(data) => {
				dispatch(appConfigSetMessage({text: data.detail}));
			});
	};
	const loginFacebook = (user) => {
		ServiceAuth.loginFacebook(user,
			(result) => {
				localStorage.setItem('token', result.token);
				dispatch(authSetUser({...result, logged: true}));
				ServiceFamily.getFamily(family => {
					dispatch(familySetData({
						...family,
						fetched: true
					}));
					history.push("/");
				});
			},
			(data) => {
				dispatch(appConfigSetMessage({text: data.detail}));
			});
	};
	const loginGoogle   = (user) => {
		ServiceAuth.loginGoogle(user,
			(result) => {
				localStorage.setItem('token', result.token);
				dispatch(authSetUser({...result, logged: true}));
				ServiceFamily.getFamily(family => {
					dispatch(familySetData({
						...family,
						fetched: true
					}));
					history.push("/");
				});
			},
			(data) => {
				dispatch(appConfigSetMessage({text: data.detail}));
			});
	};
	
	
	const onFinishFailed = errorInfo => {
		console.log('Failed:', errorInfo);
	};
	
	return (<div>
			<div style={{display: "flex", flexDirection: "column", justifyContent: "center"}}>
				<img src={Icon} style={{height: 169, width: 120, margin: 'auto'}} alt="Reportate Logo"/>
				<Form
					form={form}
					layout='vertical'
					onFinish={onFinish}
					onFinishFailed={onFinishFailed}
				>
					<Form.Item label="Nombre de usuario"
										 name="username"
										 rules={[
											 {
												 required: true, message: 'Ingresa nombre de usuario',
							
											 }
										 ]}>
						<Input placeholder="Ingresa en nombre de usuario"/>
					</Form.Item>
					<Form.Item label="Contraseña"
										 name="password"
										 rules={[{required: true, message: 'Ingresa tu contraseña'}]}>
						<Input.Password placeholder="Ingresa tu contraseña"/>
					</Form.Item>
					<Form.Item>
						<div style={{display: "flex", flexDirection: "row"}}>
							<Button type="primary" htmlType="submit"
											style={{width: '100%'}}>Iniciar sesión</Button>
						</div>
					</Form.Item>
				</Form>
				
				<FacebookLogin
					appId="590655088207979"
					autoLoad={false}
					fields="name,email"
					callback={(response) => {
						loginFacebook({
							id   : response.id,
							name : response.name,
							email: response.email
						});
					}}
					render={renderProps => (
						<Button type="default" onClick={renderProps.onClick} style={{marginBottom: 8}}>
							<p style={{color: "#3578e5"}}><FaFacebookF/> Iniciar con Facebook</p></Button>
					)}
				
				/>
				<GoogleLogin
					clientId="991838490391-29llvurok2ovuojbkk3eqj5o7tg62nd8.apps.googleusercontent.com"
					onSuccess={(response) => {
						console.log(response.profileObj);
						loginGoogle({
							id   : response.profileObj.googleId,
							name : response.profileObj.name,
							email: response.profileObj.email
						});
					}}
					onFailure={(e) => {
						console.log("fail login google");
						console.log(e);
					}}
					cookiePolicy={'single_host_origin'}
					render={renderProps => (
						<Button type="default" onClick={renderProps.onClick}>
							<p style={{color: "#eb4135"}}><FaGoogle/> Iniciar con Google</p>
						</Button>
					)}
				
				/>
			</div>
		
		</div>
	);
}
