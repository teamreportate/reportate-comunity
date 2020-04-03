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
import Gestation from "./memberData/Gestation";
import ExternalContact from "./memberData/ExternalContact";
import BaseData from "./memberData/BaseData";
import BaseSymptom from "./memberData/BaseSymptom";
import Report from "./report/Report";
import Faq from "./faq/Faq";
import Register from "./register/Register";
import EmailLogin from "./register/Login";
import DailyData from "./memberData/DailyData";
import {useSelector} from "react-redux";

const {Content, Footer} = Layout;

function App() {
	const user = useSelector(store => store.auth.user);
	// const dispatch              = useDispatch();
	// const user                  = useSelector(store => store.auth);
	// const [checked, setChecked] = useState(false);
	// const [valid, setValid]     = useState(false);
	useEffect(() => {
	
	}, []);
	
	// let content = <p>cargando</p>;
	//
	// if (user)
	// // user stored, verifying token
	// 	if (checked && !valid) {
	// 		content = <Login/>;
	// 	} else {
	// 		content = <Dashboard/>;
	// 	}
	// else
	// // no user stored so go login
	// 	content = <Login/>;
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
				<Col xs={{span: 20, offset: 2}}
						 sm={{span: 18, offset: 3}}
						 md={{span: 12, offset: 6}}
						 lg={{span: 10, offset: 7}}
						 xl={{span: 8, offset: 8}}>
					<Router>
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
							<PrivateRoute path="/gestation">
								<Gestation/>
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
							<PrivateRoute path="/register">
								<Register/>
							</PrivateRoute>
							<PrivateRoute path="/email-login">
								<EmailLogin/>
							</PrivateRoute>
							<PrivateRoute path="/daily-data">
								<DailyData/>
							</PrivateRoute>
						</Switch>
					</Router>
				</Col>
			</Row>
		</Content>
		<Footer style={{textAlign: 'center'}}>©2020 All rights reserved</Footer>
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
