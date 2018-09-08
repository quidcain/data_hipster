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
  componentDidMount() {
    this.props.getSession();
  }

  componentWillUnmount() {}

  handleValidSubmit = (event, values) => {
    this.props.runQuery(values.query);
  };

  getColumns() {
    return this.props.columns.map(v => ({ key: v, name: v }));
  }

  rowGetter = i => this.props.rows[i];

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
            <ReactDataGrid columns={this.getColumns()} rowGetter={this.rowGetter} rowsCount={this.props.rows.length} minHeight={500} />
          </Col>
        </Row>
      </div>
    );
  }
}

const mapStateToProps = ({ authentication, query }: IRootState) => ({
  account: authentication.account,
  isAuthenticated: authentication.isAuthenticated,
  columns: query.columns,
  rows: query.rows
});

const mapDispatchToProps = { getSession, runQuery, scheduleQuery };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(QueryPage);
