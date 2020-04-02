import React from 'react';
import {Button, Form, Select} from "antd";
import {useHistory} from "react-router-dom";

const {Option} = Select;

export default () => {
	let history  = useHistory();
	const [form] = Form.useForm();
	
	function handleClick() {
		history.push("/dashboard");
	}
	
	return (
		<div>
			<p>Introduce la informacion basica de tu familia</p>
			<Form
				form={form}
				layout='vertical'
			>
				<Form.Item label="¿Que sintoma presentas?">
					<Select
						showSearch
						placeholder="Seleccione una opcion"
						optionFilterProp="children"
						filterOption={(input, option) =>
							option.children.toLowerCase().indexOf(input.toLowerCase()) >= 0
						}
					>
						<Option value="op1">Tos</Option>
						<Option value="op2">Dolor de cabeza</Option>
						<Option value="op3">Fiebre</Option>
					</Select>
				</Form.Item>
				<Form.Item label="¿Desde cuando lo presentas">
					<Select
						showSearch
						placeholder="Seleccione un tiempo aproximado"
						optionFilterProp="children"
						filterOption={(input, option) =>
							option.children.toLowerCase().indexOf(input.toLowerCase()) >= 0
						}
					>
						<Option value="op1">Hoy</Option>
						<Option value="op2">Ayer</Option>
						<Option value="op3">Hace una semana</Option>
						<Option value="op3">Hace mas de una semana</Option>
					</Select>
				</Form.Item>
				<Form.Item>
					<Button type="primary" onClick={handleClick}>Continuar</Button>
				</Form.Item>
			</Form>
		</div>
	);
}