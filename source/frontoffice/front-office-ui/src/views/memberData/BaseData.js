import {Button, Checkbox, Form, Modal, Tabs} from "antd";
import React, {useEffect, useState} from "react";
import {useHistory} from "react-router-dom";
import ServiceAppConfig from "../../services/ServiceAppConfig";
import ServiceFamily from "../../services/ServiceFamily";
import {useDispatch, useSelector} from "react-redux";
import {familySetFirstControl} from "../../store/family/actions";
import {appConfigSetMessage} from "../../store/appConfig/actions";

const {TabPane} = Tabs;

export default () => {
	const [step, setStep] = useState("1");
	const [sicknesses, setSicknesses] = useState([]);
	const [symptoms, setSymptoms]     = useState([]);
	let history                       = useHistory();
	const [form]                      = Form.useForm();
	const selectedUser                = useSelector(store => store.family.toUpdate);
	const dispatch                    = useDispatch();
	useEffect(() => {
		ServiceAppConfig.getBaseData((result => {
			console.log(result);
			setSicknesses(result.enfermedadesBase);
			setSymptoms(result.sintomas);
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
		else
			history.push("/dashboard");
	}
	
	const onFinish = values => {
		if (step === "1")
			setStep("2");
		else {
			const tempSymptoms   = [];
			const tempSicknesses = [];
			
			values.sicknesses.map(sickness => {
				tempSicknesses.push({id: sickness, nombre: ""});
			});
			values.symptoms.map(symptom => {
				tempSymptoms.push({id: symptom, respuesta: true, observacion: ""});
			});
			ServiceFamily.dailyControl(selectedUser.id, tempSicknesses, tempSymptoms, (result) => {
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
													{symptom.nombre}
												</Checkbox>
												<Button type="primary" shape="circle" onClick={() => {
													info(symptom.pregunta, symptom.ayuda);
												}}>i</Button>
											</div>
										);
									})
								}
							</Checkbox.Group>
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
}