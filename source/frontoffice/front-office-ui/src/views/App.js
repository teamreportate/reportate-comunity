import React, {useEffect, useState} from 'react';
import './App.scss';
import {useDispatch, useSelector} from "react-redux";
import Dashboard from "./dashboard/Dashboard";
import Login from "./login/Login";
import {Col, Layout, Row} from "antd";
import Navigation from "../components/Navigation";
import {BrowserRouter as Router, Route, Switch} from "react-router-dom";
import Initialize from "./initialize/Initialize";
import FamilyData from "./familiyData/FamilyData";
import MemberData from "./memberData/MemberData";
import Gestation from "./memberData/Gestation";
import ExternalContact from "./memberData/ExternalContact";
import BaseData from "./memberData/BaseData";
import BaseSymptom from "./memberData/BaseSymptom";
import Report from "./report/Report";
import Faq from "./faq/Faq";

const {Header, Content, Footer} = Layout;

function App() {
	const dispatch              = useDispatch();
	const user                  = useSelector(store => store.auth);
	const [checked, setChecked] = useState(false);
	const [valid, setValid]     = useState(false);
	
	useEffect(() => {
	
	}, []);
	
	let content = <p>cargando</p>;
	
	if (user)
	// user stored, verifying token
		if (checked && !valid) {
			content = <Login/>;
		} else {
			content = <Dashboard/>;
		}
	else
	// no user stored so go login
		content = <Login/>;
	
	
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
							<Route path="/family-data">
								<FamilyData/>
							</Route>
							<Route path="/dashboard">
								<Dashboard/>
							</Route>
							<Route path="/add-member">
								<MemberData newMember={true}/>
							</Route>
							<Route path="/update-member">
								<MemberData newMember={false}/>
							</Route>
							<Route path="/gestation">
								<Gestation/>
							</Route>
							<Route path="/external-contact">
								<ExternalContact/>
							</Route>
							<Route path="/base-data">
								<BaseData/>
							</Route>
							<Route path="/base-symptom">
								<BaseSymptom/>
							</Route>
							<Route path="/report">
								<Report/>
							</Route>
							<Route path="/faq">
								<Faq/>
							</Route>
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
