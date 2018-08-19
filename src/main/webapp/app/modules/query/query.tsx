import React from 'react';

import { connect } from 'react-redux';
import { AvForm, AvField } from 'availity-reactstrap-validation';
import { Row, Col, Button } from 'reactstrap';

import { IRootState } from 'app/shared/reducers';
import { getSession } from 'app/shared/reducers/authentication';
import { runQuery } from './query.reducer';

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
    this.props.runQuery(values.query, values.dataSourceId);
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
                Start Query
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

const mapDispatchToProps = { getSession, runQuery };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(QueryPage);
