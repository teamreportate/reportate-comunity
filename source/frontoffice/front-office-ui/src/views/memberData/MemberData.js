import {Button, Form, Input, InputNumber, Radio, Select} from "antd";
import React, {useEffect, useState} from "react";
import {useHistory} from "react-router-dom";
import ServiceFamily from "../../services/ServiceFamily";
import {useDispatch, useSelector} from "react-redux";
import {familyAddMember, familyUpdateMember} from "../../store/family/actions";

const {Option} = Select;
export default ({newMember}) => {
	let history                     = useHistory();
	const dispatch                  = useDispatch();
	const [form]                    = Form.useForm();
	const [sex, setSex]             = useState(null);
	const [gestation, setGestation] = useState(false);
	const member                    = useSelector(store => store.family.toUpdate);
	
	
	function handleCancelClick() {
		history.push("/dashboard");
	}
	
	const getDefaultFields  = () => {
		const fields = [];
		for (const field in member) {
			fields.push({
				name : field,
				value: member[field]
			});
		}
		return fields;
	};
	const handleNextClick   = () => {
		if (newMember) {
			if (sex === 'hombre')
				history.push("/external-contact");
			else if (sex === 'mujer')
				history.push("/gestation");
		} else
			history.push("/dashboard");
	};
	const handleReportClick = () => {
		history.push("/daily-data");
	};
	
	useEffect(() => {
		console.log(form);
	}, [form]);
	
	
	const onFinish = values => {
		if (newMember) {
			console.log("creating member");
			ServiceFamily.registerMember(values,
				(result) => {
					dispatch(familyAddMember(result));
					history.push("/dashboard");
				});
		} else {
			console.log("updating member");
			ServiceFamily.updateMember({...values, id: member.id, firstControl: member.firstControl},
				(result) => {
					dispatch(familyUpdateMember(result));
					history.push("/dashboard");
				});
		}
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
				fields={newMember ? [] : getDefaultFields()}
			>
				<Form.Item label="Nombre"
									 name="name"
									 rules={[{required: true, message: 'Ingresa el nombre de tu familiar'}]}>
					<Input placeholder="Introduce en nombre de tu familia"/>
				</Form.Item>
				<Form.Item label="Edad"
									 name="age"
									 rules={[{required: true, message: 'Ingresa la edad '}]}>
					<InputNumber
						style={{width: '100%'}}
						defaultValue={20}
						min={0}
						max={150}
					
					/>
				</Form.Item>
				<Form.Item label="Sexo"
									 name="sex"
									 rules={[{required: true, message: 'Ingresa el genero'}]}>
					<Select
						showSearch
						placeholder="Seleccione un departamento"
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
					? <Form.Item label="Te encuentras en estado de gestacion?"
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
				{(newMember)
				 ? null : <Form.Item>
					 <Button type="default" onClick={handleReportClick} style={{width: '100%', marginBottom: 8}}
									 className='options'>Reportar
						 sintoma</Button>
				 </Form.Item>
				}
				
				<Form.Item>
					<div style={{display: "flex", flexDirection: "row"}}>
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