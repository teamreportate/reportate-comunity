import React from 'react';
import {Button, Form, Input, Select} from "antd";
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
				<Form.Item label="Nombre">
					<Input placeholder="Introduce en nombre de tu familia"/>
				</Form.Item>
				<Form.Item label="Teléfono">
					<Input placeholder="ej. 70000001"/>
				</Form.Item>
				<Form.Item label="Dirección">
					<Input placeholder="ej. 70000001"/>
				</Form.Item>
				<Form.Item label="Zona">
					<Input placeholder="ej. centro"/>
				</Form.Item>
				<Form.Item label="Departamento">
					<Select
						showSearch
						placeholder="Seleccione un departamento"
						optionFilterProp="children"
						filterOption={(input, option) =>
							option.children.toLowerCase().indexOf(input.toLowerCase()) >= 0
						}
					>
						<Option value="op1">Opcion 1</Option>
						<Option value="op2">Opcion 2</Option>
						<Option value="op3">Opcion 3</Option>
					</Select>
				</Form.Item>
				<Form.Item label="Municipio">
					<Select
						showSearch
						placeholder="Seleccione el municipio"
						optionFilterProp="children"
						filterOption={(input, option) =>
							option.children.toLowerCase().indexOf(input.toLowerCase()) >= 0
						}
					>
						<Option value="op1">Opcion 1</Option>
						<Option value="op2">Opcion 2</Option>
						<Option value="op3">Opcion 3</Option>
					</Select>
				</Form.Item>
				<Form.Item label="Ciudad">
					<Select
						showSearch
						placeholder="Seleccione la ciudad"
						optionFilterProp="children"
						filterOption={(input, option) =>
							option.children.toLowerCase().indexOf(input.toLowerCase()) >= 0
						}
					>
						<Option value="op1">Opcion 1</Option>
						<Option value="op2">Opcion 2</Option>
						<Option value="op3">Opcion 3</Option>
					</Select>
				</Form.Item>
				<Form.Item>
					<Button type="primary" onClick={handleClick}>Continuar</Button>
				</Form.Item>
			</Form>
		</div>
	);
}