import React, {useEffect, useState} from 'react';
import {Button, Form, Input, Select} from "antd";
import {useHistory} from "react-router-dom";
import ServiceFamily from "../../services/ServiceFamily";
import ServiceAppConfig from "../../services/ServiceAppConfig";
import {useDispatch} from "react-redux";
import {familySetData} from "../../store/family/actions";

const {Option} = Select;

export default () => {
	let history                               = useHistory();
	const [departments, setDepartments]       = useState([]);
	const [municipalities, setMunicipalities] = useState([]);
	const dispatch                            = useDispatch();
	const onSubmit                            = values => {
		console.log(values);
	};
	
	const [form] = Form.useForm();
	
	useEffect(() => {
		ServiceAppConfig.getDepartments((result) => {
			setDepartments(result);
			console.log(result);
		});
	}, []);
	
	const handleChange = (value) => {
		console.log(departments);
		departments.forEach((department) => {
			if (department.id === value) {
				setMunicipalities([...department.municipios]);
			}
		});
	};
	
	function handleClick() {
		ServiceFamily.register({},
			(result) => {
				console.log(result);
			});
		//history.push("/dashboard");
	}
	
	const onFinish = values => {
		console.log('Success:', values);
		ServiceFamily.register(values,
			(result) => {
				dispatch(familySetData(result));
				history.push("/dashboard");
			});
		
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
				<Form.Item label="Departamento"
									 name="department"
									 rules={[{required: true, message: 'Ingresa el departamento donde te encuentras'}]}>
					<Select
						showSearch
						placeholder="Seleccione un departamento"
						optionFilterProp="children"
						filterOption={(input, option) =>
							option.children.toLowerCase().indexOf(input.toLowerCase()) >= 0
						}
						onChange={handleChange}
					
					>
						{
							departments.map(department => {
								return <Option key={department.id} value={department.id}>{department.nombre}</Option>;
							})
						}
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
						{
							municipalities.map(municipality => {
								return <Option key={municipality.id} value={municipality.id}>{municipality.nombre}</Option>;
							})
						}
					</Select>
				</Form.Item>
				<Form.Item label="Ciudad" name="city">
					<Input placeholder="escriba su ciudad"/>
				</Form.Item>
				<Form.Item>
					<Button type="primary" htmlType="submit">Continuar</Button>
				</Form.Item>
			</Form>
		</div>
	);
}