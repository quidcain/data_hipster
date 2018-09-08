import React from 'react';

import { connect } from 'react-redux';
import { AvForm, AvField } from 'availity-reactstrap-validation';
import { Row, Col, Button } from 'reactstrap';
import ReactDataGrid from 'react-data-grid';
import { IRootState } from 'app/shared/reducers';
import { getSession } from 'app/shared/reducers/authentication';
import { runQuery, scheduleQuery } from './query.reducer';

export interface IQueryProps extends StateProps, DispatchProps {}

export interface IQueryState {
  password: string;
  rows: any;
  columns: any;
}

export class QueryPage extends React.Component<IQueryProps, IQueryState> {
  state: IQueryState = {
    password: '',
    rows: [],
    columns: []
  };

  componentDidMount() {
    this.props.getSession();
    this.createRows();
    this.state.columns = [{ key: 'id', name: 'ID' }, { key: 'title', name: 'Title' }, { key: 'count', name: 'Count' }];
  }

  componentWillUnmount() {}

  handleValidSubmit = (event, values) => {
    this.props.runQuery(values.query);
  };

  createRows = () => {
    const rows = [];
    for (let i = 1; i < 1000; i++) {
      rows.push({
        id: i,
        title: 'Title ' + i,
        count: i * 1000
      });
    }

    this.state.rows = rows;
  };

  rowGetter = i => {
    return this.state.rows[i];
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
                Run Query
              </Button>
            </AvForm>
            <ReactDataGrid columns={this.state.columns} rowGetter={this.rowGetter} rowsCount={this.state.rows.length} minHeight={500} />
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
