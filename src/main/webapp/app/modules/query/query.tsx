import React from 'react';

import { connect } from 'react-redux';
import { AvForm, AvField } from 'availity-reactstrap-validation';
import { Row, Col, Button } from 'reactstrap';

import { IRootState } from 'app/shared/reducers';
import { getSession } from 'app/shared/reducers/authentication';
import { runQuery, scheduleQuery } from './query.reducer';

export interface IQueryProps extends StateProps, DispatchProps {}

export interface IQueryState {
  password: string;
}

export class QueryPage extends React.Component<IQueryProps, IQueryState> {
  state: IQueryState = {
    password: ''
  };

  componentDidMount() {
    this.props.getSession();
  }

  componentWillUnmount() {}

  handleValidSubmit = (event, values) => {
    this.props.runQuery(values.query);
  };

  handleScheduleSubmit = (event, errors, values) => {
    this.props.scheduleQuery(values.timeMeasure, values.frequencyValue);
  };

  render() {
    const { account } = this.props;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="password-title">Get data</h2>
            <AvForm id="password-form" onValidSubmit={this.handleValidSubmit}>
              <AvField
                name="query"
                label="Query"
                placeholder="type your query here"
                type="text"
                validate={{
                  required: { value: true, errorMessage: 'Your query is required.' }
                }}
              />
              <Button color="success" type="submit">
                Preview Data (100 rows)
              </Button>
            </AvForm>
          </Col>
        </Row>
        <Row>
          <Col sm={{ size: 6, order: 2, offset: 2 }}>
            <AvForm id="schedule-form" onSubmit={this.handleScheduleSubmit}>
              <h2 id="schedule-title">Schedule</h2>
              <AvField type="select" name="timeMeasure" label="Time Scale" helpMessage="Every..">
                <option>Minute</option>
                <option>Hour</option>
                <option>Day</option>
                <option>Week</option>
                <option>Month</option>
                <option>Year</option>
              </AvField>
              <AvField name="frequencyValue" label="frequency" />
              <Button color="success" type="submit">
                Schedule Query
              </Button>
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

const mapDispatchToProps = { getSession, runQuery, scheduleQuery };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(QueryPage);
