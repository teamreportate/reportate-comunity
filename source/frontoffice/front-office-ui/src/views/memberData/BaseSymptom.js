import {Button, Checkbox, Form, Modal} from "antd";
import React from "react";
import {useHistory} from "react-router-dom";


export default () => {
	let history  = useHistory();
	const [form] = Form.useForm();
	
	function handleClick() {
		history.push("/dashboard");
	}
	
	function handleNextClick() {
		history.push("/dashboard");
	}
	
	function info() {
		Modal.info({
			title  : 'Siga las siguientes instrucciones',
			content: (
				<div>
					<p>paso 1</p>
					<p>paso 2</p>
				</div>
			),
			onOk() {
			},
		});
	}
	
	return (
		<div>
			<p>¿Padece alguna de las siguientes enfermedades de base?</p>
			<Form
				form={form}
				layout='vertical'
			>
				<Form.Item>
					<div style={{display: "flex", justifyContent: "space-between", alignItems: "center"}}>
						<Checkbox>
							Tos
						</Checkbox>
						<Button type="primary" shape="circle" onClick={info}>i</Button>
					</div>
				</Form.Item>
				<Form.Item>
					<Checkbox>Debilidad</Checkbox>
				</Form.Item>
				<Form.Item>
					<Checkbox>Dolor de cabeza</Checkbox>
				</Form.Item>
				<Form.Item>
					<Checkbox>Fatiga</Checkbox>
				</Form.Item>
				<Form.Item>
					<div style={{display: "flex", justifyContent: "space-between", alignItems: "center"}}>
						<Checkbox>
							Irritacion ocular
						</Checkbox>
						<Button type="primary" shape="circle" onClick={info}>i</Button>
					</div>
				</Form.Item>
			
			</Form>
			<Form.Item>
				<div style={{display: "flex", flexDirection: "row"}}>
					<Button type="default" onClick={handleClick}
									style={{width: '100%', marginBottom: 8, marginRight: 8}}>Cancelar</Button>
					<Button type="primary" onClick={handleNextClick}
									style={{width: '100%', marginBottom: 8, marginLeft: 8}}>Guardar</Button>
				</div>
			</Form.Item>
		</div>
	);
}
