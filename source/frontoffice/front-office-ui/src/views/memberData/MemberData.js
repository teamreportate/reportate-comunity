import {Button, Form, Input, InputNumber, Radio, Select, Spin} from "antd";
import React, {useEffect, useState} from "react";
import {useHistory} from "react-router-dom";
import ServiceFamily from "../../services/ServiceFamily";
import {useDispatch, useSelector} from "react-redux";
import {familyAddMember, familyUpdateMember} from "../../store/family/actions";
import {appConfigSetMessage} from "../../store/appConfig/actions";
import ServiceAppConfig from "../../services/ServiceAppConfig";

const {Option} = Select;
export default ({newMember}) => {
	let history                         = useHistory();
	const [occupations, setOccupations] = useState([]);
	const dispatch                      = useDispatch();
	const [form]                        = Form.useForm();
	const [sex, setSex]                 = useState(null);
	const [gestation, setGestation]     = useState(false);
	const [loading, setLoading]         = useState(false);
	const member                        = useSelector(store => store.family.toUpdate);
	const [occupation, setOccupation]   = useState(!!member.occupation);
	
	function handleCancelClick() {
		history.push("/dashboard");
	}
	
	useEffect(() => {
		ServiceAppConfig.getOccupations(result => {
			setOccupations(result);
		});
	}, []);
	
	const getDefaultFields = () => {
		const fields = [];
		for (const field in member) {
			fields.push({
				name : field,
				value: member[field]
			});
		}
		return fields;
	};
	
	const handleReportClick  = () => {
		history.push("/daily-data");
	};
	const handleHistoryClick = () => {
		history.push("/history");
	};
	
	
	const onFinish = values => {
		setLoading(true);
		if (newMember) {
			ServiceFamily.registerMember(values,
				(result) => {
					dispatch(familyAddMember(result));
					setLoading(false);
					history.push("/dashboard");
				},
				(data) => {
					dispatch(appConfigSetMessage({text: data.detail}));
					setLoading(false);
				});
			
		} else {
			console.log(values);
			ServiceFamily.updateMember({...values, id: member.id, firstControl: member.firstControl},
				(result) => {
					dispatch(familyUpdateMember(result));
					setLoading(false);
					history.push("/dashboard");
				},
				(data) => {
					dispatch(appConfigSetMessage({text: data.detail}));
					setLoading(false);
				});
		}
	};
	
	const onFinishFailed = errorInfo => {
		console.log('Failed:', errorInfo);
	};
	
	if (loading) {
		return <div style={{display: "flex", justifyContent: "center", alignItems: "center", flexDirection: "column"}}>
			<Spin/>
			<span>Guardando...</span>
		</div>;
	}
	
	return (
		<div>
			<p>Ingresa la información básica del integrante de tú familia</p>
			<Form
				form={form}
				layout='vertical'
				onFinish={onFinish}
				onFinishFailed={onFinishFailed}
				fields={newMember ? [] : getDefaultFields()}
				initialValues={{
					occupationSwitch: !!member.occupation,
					occupation      : member.occupation,
					age             : 20,
				}}
			>
				<Form.Item label="Nombre"
									 name="name"
									 rules={[
										 {required: true, message: 'Ingresa el nombre del integrante de tú familia'},
										 {max: 100, message: 'Nombre máximo 100 caracteres'},]}>
					<Input placeholder="Ingrese el nombre y apellido"/>
				</Form.Item>
				<Form.Item label="Edad"
									 name="age"
									 rules={[{required: true, message: 'Ingresa la edad'}]}>
					<InputNumber
						style={{width: '100%'}}
						min={0}
						max={150}
					
					/>
				</Form.Item>
				<Form.Item label="Sexo"
									 name="sex"
									 rules={[{required: true, message: 'Ingresa el genero'}]}>
					<Select
						showSearch
						placeholder="Seleccione una opción"
						optionFilterProp="children"
						filterOption={(input, option) =>
							option.children.toLowerCase().indexOf(input.toLowerCase()) >= 0
						}
						onChange={value => {
							setSex(value);
						}}
					>
						<Option value="MASCULINO">Hombre</Option>
						<Option value="FEMENINO">Mujer</Option>
					</Select>
				</Form.Item>
				{
					sex === 'FEMENINO'
					? <Form.Item label="¿Te encuentras en estado de gestación?"
											 name="gestation">
						<Radio.Group name="radiogroup" defaultValue={gestation} onChange={event => {
							setGestation(event.target.value);
						}}>
							<Radio value={false}>No</Radio>
							<Radio value={true}>Si</Radio>
						</Radio.Group>
					</Form.Item>
					: null
				}
				
				{
					gestation && sex === 'FEMENINO'
					? <Form.Item label="Cuantas semanas"
											 name="gestationTime"
											 rules={[{required: true, message: 'Ingresa el tiempo de gestación'}]}>
						<InputNumber
							style={{width: '100%'}}
							defaultValue={5}
							min={1}
							max={42}
						/>
					</Form.Item>
					: null
				}
				
				
				<Form.Item label="¿Es personal de salud?"
									 name="occupationSwitch">
					<Select
						showSearch
						placeholder="Seleccione una opción"
						optionFilterProp="children"
						filterOption={(input, option) =>
							option.children.toLowerCase().indexOf(input.toLowerCase()) >= 0
						}
						onChange={value => {
							setOccupation(value);
						}}
					>
						<Option value={false}>NO</Option>
						<Option value={true}>SI</Option>
					</Select>
				</Form.Item>
				
				{
					occupation
					? <Form.Item label="Ocupación"
											 name="occupation"
											 rules={[{required: true, message: 'Selecciona tu ocupación'}]}
					>
						<Select
							showSearch
							placeholder="Seleccione una ocupación"
							optionFilterProp="children"
							filterOption={(input, option) =>
								option.children.toLowerCase().indexOf(input.toLowerCase()) >= 0
							}
						>
							{
								occupations.map(occupation => {
									return <Option key={occupation.id} value={occupation.valor}>{occupation.valor}</Option>;
								})
							}</Select>
					</Form.Item>
					: null
				}
				
				
				{(newMember)
				 ? null : (
					 <>
						 <Form.Item>
							 <Button type="default" onClick={handleReportClick} style={{width: '100%', marginBottom: 8}}
											 className='options'>Reportar sintoma</Button>
						 </Form.Item>
						 <Form.Item>
							 <Button type="default" onClick={handleHistoryClick} style={{width: '100%', marginBottom: 8}}
											 className='options'>Diagnosticos</Button>
						 </Form.Item>
					 </>
				 )
					
				}
				<Form.Item>
					<div style={{display: "flex", flexDirection: "row", marginTop: 16}}>
						<Button type="default" onClick={handleCancelClick}
										style={{width: '100%', marginBottom: 8, marginRight: 8}}>Cancelar</Button>
						<Button type="primary" htmlType="submit"
										style={{width: '100%', marginBottom: 8, marginLeft: 8}}>Guardar</Button>
					</div>
				</Form.Item>
			</Form>
		</div>
	);
}