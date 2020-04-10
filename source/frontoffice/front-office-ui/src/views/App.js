import React, {useEffect} from 'react';
import './App.scss';
import Dashboard from "./dashboard/Dashboard";
import Login from "./login/Login";
import {Col, Layout, Row} from "antd";
import Navigation from "../components/Navigation";
import {BrowserRouter as Router, Redirect, Route, Switch} from "react-router-dom";
import Initialize from "./initialize/Initialize";
import FamilyData from "./familiyData/FamilyData";
import MemberData from "./memberData/MemberData";
import ExternalContact from "./memberData/ExternalContact";
import BaseData from "./memberData/BaseData";
import BaseSymptom from "./memberData/BaseSymptom";
import Report from "./report/Report";
import Faq from "./faq/Faq";
import DailyData from "./memberData/DailyData";
import {useDispatch, useSelector} from "react-redux";
import Modal from "antd/es/modal";
import {appConfigSetMessage} from "../store/appConfig/actions";
import HealthCenter from "./healthCenter/HealthCenter";

const {Content, Footer} = Layout;

function App() {
	const user     = useSelector(store => store.auth.user);
	const message  = useSelector(store => store.appConfig.message);
	const dispatch = useDispatch();
	
	useEffect(() => {
		if (message)
			Modal.info({
				title  : 'info',
				content: (
					<div>
						<p>{message.text}</p>
					</div>
				),
				onOk() {
					dispatch(appConfigSetMessage(false));
				},
			});
	}, [message]);
	
	function PrivateRoute({children, ...rest}) {
		return (
			<Route
				{...rest}
				render={({location}) =>
					user.logged ? (
						children
					) : (
						<Redirect
							to={{
								pathname: "/",
							}}
						/>
					)
				}
			/>
		);
	}
	
	return (<Layout id='theme_bus'>
		<Navigation/>
		<Content style={{marginTop: 100}}>
			<Row>
				<Col xs={{span: 22, offset: 1}}
						 sm={{span: 18, offset: 3}}
						 md={{span: 12, offset: 6}}
						 lg={{span: 10, offset: 7}}
						 xl={{span: 8, offset: 8}}>
					<Router basename={process.env.PUBLIC_URL}>
						<Switch>
							<Route exact path="/">
								<Initialize/>
							</Route>
							<Route path="/login">
								<Login/>
							</Route>
							<PrivateRoute path="/family-data">
								<FamilyData/>
							</PrivateRoute>
							<PrivateRoute path="/dashboard">
								<Dashboard/>
							</PrivateRoute>
							<PrivateRoute path="/add-member">
								<MemberData newMember={true}/>
							</PrivateRoute>
							<PrivateRoute path="/update-member">
								<MemberData newMember={false}/>
							</PrivateRoute>
							<PrivateRoute path="/external-contact">
								<ExternalContact/>
							</PrivateRoute>
							<PrivateRoute path="/base-data">
								<BaseData/>
							</PrivateRoute>
							<PrivateRoute path="/base-symptom">
								<BaseSymptom/>
							</PrivateRoute>
							<PrivateRoute path="/report">
								<Report/>
							</PrivateRoute>
							<PrivateRoute path="/faq">
								<Faq/>
							</PrivateRoute>
							<PrivateRoute path="/health-centers">
								<HealthCenter/>
							</PrivateRoute>
							<PrivateRoute path="/daily-data">
								<DailyData/>
							</PrivateRoute>
						</Switch>
					</Router>
				</Col>
			</Row>
		</Content>
		<Footer style={{
			textAlign : 'center',
			background: '#a3bf48',
			color     : '#fff',
			padding   : '16px 20px'
		}}>
			©2020 All rights reserved
		</Footer>
		<style>{`
      .logo {
				width: 120px;
				height: 32px;
				background: rgba(255, 255, 255, 0.2);
				float: left;
			}
    `}</style>
	</Layout>);
}

export default App;