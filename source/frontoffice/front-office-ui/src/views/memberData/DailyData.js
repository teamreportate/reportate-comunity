import {Button, Checkbox, Form, Modal, Select, Tabs} from "antd";
import React, {useEffect, useState} from "react";
import {useHistory} from "react-router-dom";
import ServiceAppConfig from "../../services/ServiceAppConfig";
import ServiceFamily from "../../services/ServiceFamily";
import {useDispatch, useSelector} from "react-redux";
import {appConfigSetMessage} from "../../store/appConfig/actions";

const {TabPane} = Tabs;
const {Option}  = Select;
export default ({newMember}) => {
	const [symptoms, setSymptoms] = useState([]);
	let history                   = useHistory();
	const dispatch                = useDispatch();
	const [form]                  = Form.useForm();
	const selectedUser            = useSelector(store => store.family.toUpdate);
	
	useEffect(() => {
		ServiceAppConfig.getSymptoms((result => {
			setSymptoms(result);
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
		history.push("/dashboard");
	}
	
	const onFinish = values => {
		const tempSymptoms = [];
		values.symptoms.map(symptom => {
			tempSymptoms.push({id: symptom, respuesta: true, observacion: ""});
		});
		ServiceFamily.dailyControl(selectedUser.id, [], tempSymptoms, (result) => {
			console.log(result);
			dispatch(appConfigSetMessage({text: result, type: "success"}));
			history.push("/dashboard");
		});
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
				<p>Presentas alguno de los siguientes sintomas?</p>
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