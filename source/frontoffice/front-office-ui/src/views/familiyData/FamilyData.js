import React, {useEffect, useState} from 'react';
import {Button, Form, Input, Select} from "antd";
import {useHistory} from "react-router-dom";
import ServiceFamily from "../../services/ServiceFamily";
import ServiceAppConfig from "../../services/ServiceAppConfig";
import {useDispatch} from "react-redux";
import {familySetData} from "../../store/family/actions";
import {geolocated} from "react-geolocated";
import {appConfigSetMessage} from "../../store/appConfig/actions";

const {Option} = Select;

const FamilyData = (props) => {
	let history                               = useHistory();
	const [departments, setDepartments]       = useState([]);
	const [municipalities, setMunicipalities] = useState([]);
	const [healthCenters, setHealthCenters]   = useState([]);
	const [coords, setCoords]                 = useState({latitude: 0, longitude: 0});
	const dispatch                            = useDispatch();
	const [values, setValues]                 = useState(null);
	
	const [form] = Form.useForm();
	
	useEffect(() => {
		ServiceAppConfig.getDepartments((result) => {
			setDepartments(result);
		});
	}, []);
	
	useEffect(() => {
		if (props.isGeolocationAvailable && props.isGeolocationEnabled && props.coords) {
			setCoords({
				latitude : props.coords.latitude,
				longitude: props.coords.longitude,
			});
		}
	}, [props.isGeolocationAvailable, props.isGeolocationEnabled, props.coords]);
	
	const handleDepartmentChange   = (value) => {
		departments.forEach((department) => {
			if (department.id === value) {
				setMunicipalities([...department.municipios]);
				form.setFieldsValue({'municipality': null});
			}
		});
	};
	const handleMunicipalityChange = (value) => {
		municipalities.forEach((municipality) => {
			if (municipality.id === value) {
				setHealthCenters([...municipality.centroSaluds]);
				form.setFieldsValue({'healthCenter': null});
			}
		});
	};
	
	const onFinish = values => {
		setValues(values);
		ServiceFamily.register({...values, 'latitude': coords.latitude, 'longitude': coords.longitude},
			(result) => {
				dispatch(familySetData(result));
				history.push("/dashboard");
				return true;
			},
			(data) => {
				dispatch(appConfigSetMessage({text: data.detail}));
				form.setFieldsValue(values);
			});
		return false;
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
									 rules={
										 [
											 {required: true, message: 'Ingresa el telefono'},
											 {max: 8, message: 'Telefono maximo 8 caracteres'},]
									 }
				>
					<Input placeholder="ej. 70000001"/>
				</Form.Item>
				<Form.Item label="Dirección"
									 name="address"
									 rules={[{required: true, message: 'Ingresa la dirección'}]}
				>
					<Input placeholder="ej. calle nro 10"/>
				</Form.Item>
				<Form.Item label="Zona"
									 name="zone"
									 rules={[{required: true, message: 'Ingresa la zona donde vivies'}]}
				>
					<Input placeholder="ej. centro"/>
				</Form.Item>
				<Form.Item label="Departamento"
									 name="department"
									 rules={[{required: true, message: 'Ingresa el departamento donde te encuentras'}]}
				>
					<Select
						showSearch
						placeholder="Seleccione un departamento"
						optionFilterProp="children"
						filterOption={(input, option) =>
							option.children.toLowerCase().indexOf(input.toLowerCase()) >= 0
						}
						onChange={handleDepartmentChange}
					
					>
						{
							departments.map(department => {
								return <Option key={department.id} value={department.id}>{department.nombre}</Option>;
							})
						}
					</Select>
				</Form.Item>
				<Form.Item label="Municipio"
									 name="municipality"
									 rules={[{required: true, message: 'Ingresa el municipio donde te encuentras'}]}>
					<Select
						showSearch
						placeholder="Seleccione el municipio"
						optionFilterProp="children"
						filterOption={(input, option) =>
							option.children.toLowerCase().indexOf(input.toLowerCase()) >= 0
						}
						onChange={handleMunicipalityChange}
					>
						{
							municipalities.map(municipality => {
								return <Option key={municipality.id} value={municipality.id}>{municipality.nombre}</Option>;
							})
						}
					</Select>
				</Form.Item>
				<Form.Item label="Centro de salud"
									 name="healthCenter"
									 rules={[{required: true, message: 'Ingresa el centro de salud'}]}
				>
					<Select
						showSearch
						placeholder="Seleccione el centro de salud"
						optionFilterProp="children"
						filterOption={(input, option) =>
							option.children.toLowerCase().indexOf(input.toLowerCase()) >= 0
						}
					>
						{
							healthCenters.map(healthCenter => {
								return <Option key={healthCenter.id} value={healthCenter.id}>{healthCenter.nombre}</Option>;
							})
						}
					</Select>
				</Form.Item>
				
				<Form.Item
					label="Ciudad"
					name="city"
					rules={[{required: true, message: 'Ingresa tu ciudad'}]}
				>
					<Input placeholder="escriba su ciudad"/>
				</Form.Item>
				<Form.Item>
					<Button type="primary" htmlType="submit">Continuar</Button>
				</Form.Item>
			</Form>
		</div>
	);
};

export default geolocated({
	positionOptions    : {
		enableHighAccuracy: false,
	},
	userDecisionTimeout: 5000,
})(FamilyData);