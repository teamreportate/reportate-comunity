import React from "react";
import {Layout} from 'antd';

const {Header} = Layout;


export default () => {
	return (
		<Header className='primary' style={{
			position      : 'fixed',
			zIndex        : 1,
			width         : '100%',
			display       : 'flex',
			height        : 60,
			justifyContent: 'center',
			alignItems    : 'center',
		}}>
			
			<h2 style={{margin: 0}}>Reportate</h2>
		
		</Header>
	);
}