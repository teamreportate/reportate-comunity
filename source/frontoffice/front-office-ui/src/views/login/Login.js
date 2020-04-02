import React, {useEffect} from "react";
import FacebookLogin from 'react-facebook-login/dist/facebook-login-render-props';
import GoogleLogin from 'react-google-login';
import {Button} from "antd";

export default () => {
	useEffect(() => {
		//TODO validate if login redirect to dashboard, else redirect to loginpage
	}, []);
	return (<div>
		<div style={{display: "flex", flexDirection: "column"}}>
			<FacebookLogin
				appId="1088597931155576"
				autoLoad={false}
				fields="name,email,picture"
				render={renderProps => (
					<Button type="default" onClick={renderProps.onClick} style={{marginBottom: 8}}>
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
					<Button type="default" onClick={renderProps.onClick}>
						<p style={{color: "red"}}>Iniciar con Google</p>
					</Button>
				)}
			/>
		
		
		</div>
	
	</div>);
}