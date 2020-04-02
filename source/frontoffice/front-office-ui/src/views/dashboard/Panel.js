import React from 'react';
import {Button, Col, Row} from "antd";

export default () => {
	return <Row>
		<Col xs={12} sm={12} md={{span: 8, offset: 5}} lg={{span: 6, offset: 7}} xl={{span: 6, offset: 7}}>
			<div>
				Lista de miembros de familia
			</div>
		</Col>
		
		<Col xs={12} sm={12} md={{span: 8, offset: 5}} lg={{span: 6, offset: 7}} xl={{span: 6, offset: 7}}>
			<div>
				<Button size={'large'} type="default" className='options'>Centros de atencion</Button>
				<Button size={'large'} type="default" className='options'>Recomendaciones</Button>
			</div>
		</Col>
		<Col xs={12} sm={12} md={{span: 8, offset: 5}} lg={{span: 6, offset: 7}} xl={{span: 6, offset: 7}}>
			<div>
				<Button size={'large'} type="default" className='options'>
				<small>Nmero de emergencia 1</small>
					800 123456
				</Button>
				<Button size={'large'} type="default" className='options'>
				<small>Nmero de emergencia 2</small>
					800 123457
				</Button>
			</div>
		</Col>
	</Row>;
}