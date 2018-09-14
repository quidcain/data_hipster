import React from 'react';
import { Row, Col, Card, CardText, CardBody, CardTitle, CardSubtitle } from 'reactstrap';
import './datasource.scss';

export default ({ datasources }) => (
  <Row className="justify-content-center">
    <Col md="8" className="d-flex flex-wrap justify-content-center">
      {datasources.map((ds, i) => (
        <Card key={i} className="datasource">
          <CardBody>
            <CardTitle>{ds.name}</CardTitle>
            <CardSubtitle>{ds.config.connection}</CardSubtitle>
            <CardText>{Object.values(ds.config.workspaces).map((value, j) => <WorkspaceConfig key={j} config={value} />)}</CardText>
          </CardBody>
        </Card>
      ))}
    </Col>
  </Row>
);

const WorkspaceConfig = ({ config }) => (
  <>
    location: {config.location}
    <br />
    writable: {String(config.writable)}
    <br />
    defaultInputFormat: {config.defaultInputFormat}
    <br />
    allowAccessOutsideWorkspace: {String(config.allowAccessOutsideWorkspace)}
    <br />
  </>
);
