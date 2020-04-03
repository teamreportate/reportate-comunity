import React, {useEffect} from 'react';
import {Button, Form, Input, Select} from "antd";
import {useHistory} from "react-router-dom";
import ServiceFamily from "../../services/ServiceFamily";

const {Option} = Select;

export default () => {
	let history = useHistory();
	
	const onSubmit = values => {
		console.log(values);
	};
	
	const [form] = Form.useForm();
	
	useEffect(() => {
	}, []);
	
	
	function handleClick() {
		ServiceFamily.register({},
			(result) => {
				console.log(result);
			});
		//history.push("/dashboard");
	}
	
	const onFinish = values => {
		console.log('Success:', values);
	};
	
	const onFinishFailed = errorInfo => {
		console.log('Failed:', errorInfo);
	};
	
	return (
		<div>
			<p>Introduce la informacion basica de tu familia</p>
			<Form
				form={form}
				layout='vertical'
				onFinish={onFinish}
				onFinishFailed={onFinishFailed}
			>
				<Form.Item label="Nombre"
									 name="name"
									 rules={[{required: true, message: 'Ingresa el nombre de tu familia'}]}
				>
					<Input placeholder="Introduce en nombre de tu familia"/>
				</Form.Item>
				<Form.Item label="Teléfono"
									 name="phone"
				>
					<Input placeholder="ej. 70000001"/>
				</Form.Item>
				<Form.Item label="Dirección" name="address">
					<Input placeholder="ej. 70000001"/>
				</Form.Item>
				<Form.Item label="Zona" name="zone">
					<Input placeholder="ej. centro"/>
				</Form.Item>
				<Form.Item label="Departamento" name="department">
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
				<Form.Item label="Municipio" name="municipality">
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
				<Form.Item label="Ciudad" name="city">
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
					<Button type="primary" htmlType="submit">Continuar</Button>
				</Form.Item>
			</Form>
		</div>
	);
}