import React from 'react';

import { connect } from 'react-redux';
import { AvForm, AvField } from 'availity-reactstrap-validation';
import { Row, Col, Button } from 'reactstrap';

import { IRootState } from 'app/shared/reducers';
import { getSession } from 'app/shared/reducers/authentication';
import { testDataSource } from './datasource.reducer';

export interface IDataSourceProps extends StateProps, DispatchProps {}

export interface IDataSourceState {
  hostname: string;
  username: string;
  password: string;
  schema: string;
}

export class QueryPage extends React.Component<IDataSourceProps, IDataSourceState> {
  state: IDataSourceState = {
    hostname: '',
    username: '',
    password: '',
    schema: ''
  };

  componentDidMount() {
    this.props.getSession();
  }

  componentWillUnmount() {}

  handleValidSubmit = (event, values) => {
    this.props.testDataSource(values.hostname, values.username, values.password, values.schema);
  };

  testDataSource = (event, errors, values) => {
    event.preventDefault();
    this.props.testDataSource(this.state.hostname, this.state.username, this.state.password, this.state.schema);
  };

  render() {
    const { account } = this.props;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="datasource-title">Create DataSource</h2>
            <AvForm id="datasource-form" onValidSubmit={this.handleValidSubmit}>
              <AvField
                value={this.state.hostname}
                name="hostname"
                id="hostname"
                label="Hostname"
                placeholder="Name or IP address of server"
                type="text"
                validate={{ required: { value: true, errorMessage: 'We need to know what database to connect to!' } }}
              />
              <AvField
                value={this.state.hostname}
                name="schema"
                id="schema"
                label="Schema"
                placeholder="Schema/Database name"
                type="text"
                validate={{ required: { value: true, errorMessage: 'We need to know what schema to connect to!' } }}
              />
              <AvField
                value={this.state.username}
                name="username"
                label="User"
                placeholder="Database account username"
                type="text"
                validate={{ required: { value: true, errorMessage: 'You must specify a user' } }}
              />
              <AvField
                onChange={this.state.password}
                name="password"
                label="Password"
                placeholder="Database account password"
                type="password"
                validate={{
                  required: { value: true, errorMessage: 'You must specify a user' }
                }}
              />
              <Row>
                <Col>
                  <Button color="success" type="submit">
                    Save Data Source
                  </Button>
                </Col>
                <Col>
                  <AvForm id="datasource-test-form" onSubmit={this.testDataSource}>
                    <Button color="blue">Test Connection</Button>
                  </AvForm>
                </Col>
              </Row>
            </AvForm>
          </Col>
        </Row>
      </div>
    );
  }
}

const mapStateToProps = ({ authentication }: IRootState) => ({
  account: authentication.account,
  isAuthenticated: authentication.isAuthenticated
});

const mapDispatchToProps = { getSession, testDataSource };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(QueryPage);
