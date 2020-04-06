import {Button, Checkbox, Form, Modal, Select, Tabs, Tag} from "antd";
import React, {useEffect, useState} from "react";
import {useHistory} from "react-router-dom";
import ServiceAppConfig from "../../services/ServiceAppConfig";
import ServiceFamily from "../../services/ServiceFamily";
import {useDispatch, useSelector} from "react-redux";
import {familySetFirstControl} from "../../store/family/actions";
import {appConfigSetMessage} from "../../store/appConfig/actions";

const {Option,} = Select;
const {TabPane} = Tabs;

const BaseData = () => {
	const [step, setStep]             = useState("1");
	const [sicknesses, setSicknesses] = useState([]);
	const [symptoms, setSymptoms]     = useState([]);
	const [countries, setCountries]   = useState([]);
	let history                       = useHistory();
	const [form]                      = Form.useForm();
	const selectedUser                = useSelector(store => store.family.toUpdate);
	const dispatch                    = useDispatch();
	useEffect(() => {
		ServiceAppConfig.getBaseData((result => {
			console.log(result);
			setSicknesses(result.enfermedadesBase);
			setSymptoms(result.sintomas);
			setCountries(result.paises);
		}));
	}, []);
	
	function info(title, content) {
		Modal.info({
			title  : title,
			content: (
				<div>
					<p>{content}</p>
				</div>
			),
			onOk() {
			},
		});
	}
	
	function handleBackClick() {
		if (step === "2")
			setStep("1");
		else if (step === "3")
			setStep("2");
		else
			history.push("/dashboard");
	}
	
	const onFinish = values => {
		if (step === "1")
			setStep("2");
		else if (step === "2")
			setStep("3");
		else {
			const tempSymptoms   = [];
			const tempSicknesses = [];
			const tempCountries  = [];
			console.log(values);
			if (values.sicknesses)
				values.sicknesses.forEach(sickness => {
					tempSicknesses.push({id: sickness, nombre: ""});
				});
			if (values.symptoms)
				values.symptoms.forEach(symptom => {
					tempSymptoms.push({id: symptom, respuesta: true, observacion: ""});
				});
			if (values.countries)
				values.countries.forEach(country => {
					tempCountries.push({id: country, nombre: ""});
				});
			
			
			ServiceFamily.dailyControl(selectedUser.id, tempSicknesses, tempSymptoms, tempCountries, (result) => {
				dispatch(familySetFirstControl(selectedUser));
				dispatch(appConfigSetMessage({text: result, type: "success"}));
				history.push("/dashboard");
			});
			
		}
		
	};
	
	const onFinishFailed = errorInfo => {
		console.log('Failed:', errorInfo);
	};
	
	return (
		<div>
			<Form
				form={form}
				layout='vertical'
				onFinish={onFinish}
				onFinishFailed={onFinishFailed}
			>
				<Tabs activeKey={step}>
					<TabPane tab="Enfermedades base" key="1">
						<p>¿Padece de alguna de las siguientes emfermades?</p>
						<Form.Item name={'sicknesses'}>
							<Checkbox.Group>
								{
									sicknesses.map(sickness => {
										return (
											<div key={sickness.id} style={{width: '100%', padding: 8}}>
												<Checkbox key={sickness.id} value={sickness.id}>{sickness.nombre}</Checkbox>
											</div>
										);
									})
								}
							</Checkbox.Group>
						</Form.Item>
					</TabPane>
					<TabPane tab="Sintomas iniciales" key="2">
						<p>Padece de alguna de las siguientes emfermades</p>
						<Form.Item name={'symptoms'}>
							<Checkbox.Group style={{width: '100%'}}>
								{
									symptoms.map(symptom => {
										return (
											<div key={symptom.id}
													 style={{
														 display       : "flex",
														 justifyContent: "space-between",
														 width         : '100%',
														 alignItems    : "center",
														 padding       : 8
													 }}>
												<Checkbox value={symptom.id}>
													{symptom.pregunta}
												</Checkbox>
												{
													symptom.ayuda
													? <Button type="primary" shape="circle" onClick={() => {
														info(symptom.pregunta, symptom.ayuda);
													}}>i</Button>
													: null
												}
											
											</div>
										);
									})
								}
							</Checkbox.Group>
						</Form.Item>
					</TabPane>
					<TabPane tab="Viaje" key="3">
						<p>¿Estuvo fuera del pais en el ultimo mes?</p>
						<Form.Item name={'countries'}>
							<Select
								mode="multiple"
								style={{width: '100%'}}
								placeholder="Seleccione paises que visito"
								onChange={(e) => {
									console.log(e);
								}}
								optionLabelProp="label"
							>
								{
									countries.map(country => {
										return (
											<Option key={country.id} value={country.id} label={country.nombre}>{country.nombre}</Option>
										);
									})
								}
							</Select>
						</Form.Item>
					</TabPane>
				</Tabs>,
				<Form.Item>
					<div style={{display: "flex", flexDirection: "row"}}>
						<Button type="default" onClick={handleBackClick}
										style={{width: '100%', marginBottom: 8, marginRight: 8}}>Cancelar</Button>
						<Button type="primary" htmlType="submit"
										style={{width: '100%', marginBottom: 8, marginLeft: 8}}>Guardar</Button>
					</div>
				</Form.Item>
			</Form>
		</div>
	);
};
export default BaseData;