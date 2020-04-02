import {Button, Checkbox, Form, Select} from "antd";
import React from "react";
import {useHistory} from "react-router-dom";

const {Option} = Select;
export default ({newMember}) => {
	let history  = useHistory();
	const [form] = Form.useForm();
	
	function handleClick() {
		history.push("/dashboard");
	}
	function handleNextClick() {
		history.push("/base-symptom");
	}
	
	return (
		<div>
			<p>¿Padece de alguna de las siguientes emfermades?</p>
			<Form
				form={form}
				layout='vertical'
			>
				<Form.Item>
					<Checkbox>Diabetes</Checkbox>
				</Form.Item>
				<Form.Item>
					<Checkbox>Chagas</Checkbox>
				</Form.Item>
				<Form.Item>
					<Checkbox>Hipertensión</Checkbox>
				</Form.Item>
				<Form.Item>
					<Checkbox>Tuberculosis</Checkbox>
				</Form.Item>
				<Form.Item>
					<Checkbox>Cancer</Checkbox>
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